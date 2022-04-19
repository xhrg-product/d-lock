package com.github.xhrg.dlock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.sql.DataSource;

public class MysqlLock implements Dlock {

    private DataSource dataSource;

    private ConcurrentMap<String, Boolean> checkTable = new ConcurrentHashMap<String, Boolean>();

    public MysqlLock(DataSource dataSource) throws DlockException {
        this.dataSource = dataSource;
    }

    @Override
    public boolean tryLock(String lockName, int timeout) throws DlockException {
        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareCall("update d_lock set `status` = 'lock', lock_time = ? "
                + "  where `name` = ? and ( `status` = 'unlock' or lock_time < ? )");
            ps.setObject(1, timeString(localDateTime));
            ps.setObject(2, lockName);
            ps.setObject(3, timeString(localDateTime.minusMinutes(timeout)));
            int size = ps.executeUpdate();
            if (size > 0) {
                return true;
            }
            if (Boolean.TRUE.equals(checkTable.get(lockName))) {
                return false;
            }
            PreparedStatement ps1 = connection.prepareStatement(" select `name` from d_lock ");
            ResultSet rs = ps1.executeQuery();
            while (rs.next()) {
                String lockNameDb = rs.getString("name");
                checkTable.put(lockNameDb, true);
            }
            if (Boolean.TRUE.equals(checkTable.get(lockName))) {
                return false;
            }

            PreparedStatement ps3 = connection.prepareCall(" insert into d_locl value ()");
            ps3.setObject(size, ps3);
            int i = ps3.executeUpdate();

            return false;
        } catch (SQLException e) {
            throw new DlockException("get connection exception", e);
        }
    }

    @Override
    public void unlock() {}

    @Override
    public void lock(String lockName, int timeout) throws DlockException {

    }

    public String timeString(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
