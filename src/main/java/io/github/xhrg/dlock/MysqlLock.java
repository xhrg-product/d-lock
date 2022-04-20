package io.github.xhrg.dlock;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.sql.DataSource;

public class MysqlLock implements DLock {

    private DataSource dataSource;

    private ConcurrentMap<String, Boolean> checkTable = new ConcurrentHashMap<String, Boolean>();

    public MysqlLock(DataSource dataSource) throws DLockException {
        this.dataSource = dataSource;
    }

    @Override
    public boolean tryLock(String lockName, int timeout) throws DLockException {
        Connection connection = null;
        try {
            LocalDateTime localDateTime = LocalDateTime.now();

            String nowString = timeString(localDateTime);

            connection = this.dataSource.getConnection();// DataSourceUtils.getConnection(dataSource);
            int size = SqlExecutor.update(connection, SqlCommand.SQL_LOCK, nowString, lockName,
                timeString(localDateTime.minusSeconds(timeout)));
            if (size > 0) {
                return true;
            }
            if (Boolean.TRUE.equals(checkTable.get(lockName))) {
                return false;
            }
            List<Map<String, Object>> list = SqlExecutor.query(connection, SqlCommand.SQL_SELECT_NAME);
            for (Map<String, Object> m : list) {
                checkTable.put((String)m.get("name"), true);
            }
            if (Boolean.TRUE.equals(checkTable.get(lockName))) {
                return false;
            }
            size = SqlExecutor.update(connection, SqlCommand.SQL_INSERT, lockName, nowString);
            if (size > 0) {
                checkTable.put(lockName, true);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new DLockException("tryLock", e);
        } finally {
            SqlExecutor.close(connection);
        }
    }

    @Override
    public void unlock(String lockName) throws DLockException {
        Connection connection = null;
        try {
            connection = this.dataSource.getConnection();// DataSourceUtils.getConnection(dataSource);
            SqlExecutor.update(connection, SqlCommand.SQL_UNLOCK, lockName);
        } catch (Exception e) {
            throw new DLockException("unlock", e);
        } finally {
            SqlExecutor.close(connection);
        }

    }

    @Override
    public void lock(String lockName, int timeout) throws DLockException {
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
