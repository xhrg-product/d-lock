package com.github.xhrg.dlock;

import com.mysql.cj.jdbc.MysqlDataSource;

public class Main {

    public static void main(String[] args) throws DlockException {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setUrl("jdbc:mysql://127.0.0.1:3306/my_test_db");
        ds.setPassword("123456");
        ds.setUser("root");
        DLock dlock = new MysqlLock(ds);
        dlock.tryLock("name-a", 10000);
    }
}
