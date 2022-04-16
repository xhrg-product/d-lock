package com.github.xhrg.dlock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

public class MysqlLock implements Dlock {

    private DataSource dataSource;

    public MysqlLock(DataSource dataSource) throws DlockException {
        this.dataSource = dataSource;
    }

    @Override
    public boolean tryLock(String lockName, int timeoutms, String remark) throws DlockException {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement ps =
                connection.prepareCall("update d_lock set `status` = 'lock', lock_time = ?, remark = ? "
                    + "  where `name` = ? and ( `status` = 'lock' or lock_time - ? > ? )");
            boolean ok = ps.execute();
            if (ok) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new DlockException("get connection exception", e);
        }
    }

    @Override
    public void unlock() {}

    @Override
    public void lock(String lockName, int timeoutms, String remark) throws DlockException {

    }

}
