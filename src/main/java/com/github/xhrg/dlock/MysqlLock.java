package com.github.xhrg.dlock;

import javax.sql.DataSource;

public class MysqlLock implements Dlock {

    private DataSource dataSource;

    public MysqlLock(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void lock(String lockName, String remark) {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public void unlock() {}

}
