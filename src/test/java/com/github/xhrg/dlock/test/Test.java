package com.github.xhrg.dlock.test;

import com.mysql.cj.jdbc.MysqlDataSource;

import io.github.xhrg.dlock.DLock;
import io.github.xhrg.dlock.DLockException;
import io.github.xhrg.dlock.MysqlLock;

public class Test {

    public static void main(String[] args) throws DLockException {

        MysqlDataSource ds = new MysqlDataSource();
        ds.setUrl("jdbc:mysql://127.0.0.1:3306/my_test_db");
        ds.setPassword("123456");
        ds.setUser("root");
        DLock dlock = new MysqlLock(ds);
        dlock.tryLock("name-a", 10000);
    }
}
