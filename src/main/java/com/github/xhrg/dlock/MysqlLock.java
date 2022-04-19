package com.github.xhrg.dlock;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;

public class MysqlLock implements DLock {

    private DataSource dataSource;

    private ConcurrentMap<String, Boolean> checkTable = new ConcurrentHashMap<String, Boolean>();

    public MysqlLock(DataSource dataSource) throws DlockException {
        this.dataSource = dataSource;
    }

    @Override
    public boolean tryLock(String lockName, int timeout) throws DlockException {
        Connection connection = null;
        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            connection = DataSourceUtils.getConnection(dataSource);
            int size = SqlHelper.update(connection, SqlCommd.SQL_LOCK, timeString(localDateTime), lockName,
                timeString(localDateTime.minusSeconds(timeout)));
            if (size > 0) {
                return true;
            }
            if (Boolean.TRUE.equals(checkTable.get(lockName))) {
                return false;
            }
            List<Map<String, Object>> list = SqlHelper.query(connection, SqlCommd.SQL_SELECT_NAME);
            for (Map<String, Object> m : list) {
                checkTable.put((String)m.get("name"), true);
            }
            if (Boolean.TRUE.equals(checkTable.get(lockName))) {
                return false;
            }
            size = SqlHelper.update(connection, SqlCommd.SQL_INSERT, lockName, timeString(localDateTime));
            if (size > 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new DlockException("tryLock", e);
        } finally {
            SqlHelper.close(connection);
        }
    }

    @Override
    public void unlock(String lockName) throws DlockException {
        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection(dataSource);
            SqlHelper.update(connection, SqlCommd.SQL_UNLOCK, lockName);
        } catch (Exception e) {
            throw new DlockException("unlock", e);
        } finally {
            SqlHelper.close(connection);
        }

    }

    @Override
    public void lock(String lockName, int timeout) throws DlockException {
        while (!tryLock(lockName, timeout)) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String timeString(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
