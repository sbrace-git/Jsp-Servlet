package com.example.jdbcmetadata;

import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.SQLException;

public class FilteredRowSetTest {
    public static void main(String[] args) {

        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String url = "jdbc:h2:tcp://localhost:9092/D:/server/H2/data/test";
        String user = "sa";
        String password = "";
        String querySql = "select * from t_message";
        try {
            FilteredRowSet filteredRowSet = RowSetProvider.newFactory().createFilteredRowSet();
            filteredRowSet.setUrl(url);
            filteredRowSet.setUsername(user);
            filteredRowSet.setPassword(password);
            filteredRowSet.setCommand(querySql);
            filteredRowSet.setFilter(new MessagePredicate(filteredRowSet, "11", "name"));
//            filteredRowSet.setFilter(new MessagePredicate(filteredRowSet, "11", 2));
            filteredRowSet.execute();
            while (filteredRowSet.next()) {
                Message message = new Message();
                message.setId(filteredRowSet.getLong(1));
                message.setName(filteredRowSet.getString(2));
                message.setEmail(filteredRowSet.getString(3));
                message.setMsg(filteredRowSet.getString(4));
                System.out.println(message);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

