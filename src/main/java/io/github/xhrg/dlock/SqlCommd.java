package io.github.xhrg.dlock;

public class SqlCommd {

    public static String SQL_LOCK =
        "update database_lock set `status` = 'lock', `time` = ? where `name` = ? and ( `status` = 'unlock' or `time` < ? )";

    public static String SQL_SELECT_NAME = "select `name` from database_lock";

    public static String SQL_INSERT = "insert into database_lock ( `name`, `status` , `time`) values ( ? , 'lock',  ?)";

    public static String SQL_UNLOCK = "update database_lock set `status` = 'unlock' where `name` = ?";
}
