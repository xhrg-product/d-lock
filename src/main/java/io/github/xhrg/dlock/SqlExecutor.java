package io.github.xhrg.dlock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlExecutor {

    public static List<Map<String, Object>> query(Connection connection, String sql, Object... param) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            for (int i = 0; i < param.length; i++) {
                ps.setObject(i + 1, param[i]);
            }
            rs = ps.executeQuery();
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            ResultSetMetaData meta = rs.getMetaData();
            int cot = meta.getColumnCount();

            while (rs.next()) {
                Map<String, Object> map = new HashMap<String, Object>();
                for (int i = 0; i < cot; i++) {
                    map.put(meta.getColumnName(i + 1), rs.getObject(i + 1));
                }
                list.add(map);
            }
            return list;
        } catch (SQLException e) {
            throw e;
        } finally {
            close(rs);
            close(ps);
        }
    };

    public static int update(Connection connection, String sql, Object... param) throws Exception {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            if (param == null) {
                param = new Object[0];
            }
            for (int i = 0; i < param.length; i++) {
                ps.setObject(i + 1, param[i]);
            }
            return ps.executeUpdate();
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate")) {
                return 0;
            }
            throw e;
        } finally {
            close(ps);
        }
    };

    public static void close(AutoCloseable c) {
        if (c != null) {
            try {
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
