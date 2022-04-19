package com.github.xhrg.dlock.test;

import com.github.xhrg.dlock.DLock;
import com.github.xhrg.dlock.DLockException;
import com.github.xhrg.dlock.MysqlLock;
import com.mysql.cj.jdbc.MysqlDataSource;

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
