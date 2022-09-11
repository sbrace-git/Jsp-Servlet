package com.example.jdbcmetadata;

import org.h2.util.StringUtils;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuestBookBeanRowSet implements Serializable {

    private JdbcRowSet jdbcRowSet;

    public GuestBookBeanRowSet() throws SQLException {
        System.out.println("class load");
        RowSetFactory rowSetFactory = RowSetProvider.newFactory();
        jdbcRowSet = rowSetFactory.createJdbcRowSet();
        jdbcRowSet.setDataSourceName("java:/comp/env/jdbc/demo");
    }

    private static final String insertSql = "insert into t_message(name,email,msg) values (?,?,?)";
    private static final String querySql = "select * from t_message ";

    public void addMessage(Message message) {
        try {
            jdbcRowSet.setCommand(querySql + " limit 0");
            jdbcRowSet.execute();
            jdbcRowSet.moveToInsertRow();
            jdbcRowSet.updateString(2, message.getName());
            jdbcRowSet.updateString(3, message.getEmail());
            jdbcRowSet.updateString(4, message.getMsg());
            jdbcRowSet.insertRow();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Message> messageList(String searchName) {
        String sql = querySql;
        if (!StringUtils.isNullOrEmpty(searchName)) {
            sql = querySql + " where name = ?";
        }
        try {
            jdbcRowSet.setCommand(sql);
            if (!StringUtils.isNullOrEmpty(searchName)) {
                jdbcRowSet.setString(1, searchName);
            }
            jdbcRowSet.execute();
            ArrayList<Message> messages = new ArrayList<>();
            while (jdbcRowSet.next()) {
                Message message = new Message();
                message.setId(jdbcRowSet.getLong(1));
                message.setName(jdbcRowSet.getString(2));
                message.setEmail(jdbcRowSet.getString(3));
                message.setMsg(jdbcRowSet.getString(4));
                messages.add(message);
            }
            return messages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
