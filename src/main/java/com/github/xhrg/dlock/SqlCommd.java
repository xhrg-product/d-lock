package com.github.xhrg.dlock;

public class SqlCommd {

    public static String SQL_LOCK =
        "update d_lock set `status` = 'lock', `time` = ? where `name` = ? and ( `status` = 'unlock' or `time` < ? )";

    public static String SQL_SELECT_NAME = "select `name` from d_lock";

    public static String SQL_INSERT = "insert into d_lock ( `name`, `status` , `time`) values ( ? , 'lock',  ?)";

    public static String SQL_UNLOCK = "update d_lock set `status` = 'unlock' where `name` = ?";
}
