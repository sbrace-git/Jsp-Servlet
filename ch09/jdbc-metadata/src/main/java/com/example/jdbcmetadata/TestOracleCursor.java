package com.example.jdbcmetadata;

import java.sql.*;

public class TestOracleCursor {
    public static void main(String[] args) {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String url = "jdbc:oracle:thin:@localhost:1521:orcl?useCursorFetch=true";
        String user = "system";
        String password = "1111";
        String querySql = "select * from t_message";
        String insert = "insert into t_message(id,name,email,msg) values (T_MESSAGE_ID_SEQ.NEXTVAL,'justin','justin@mail.com','message...')";
        /**
         * CREATE SEQUENCE T_MESSAGE_ID_SEQ
         * INCREMENT BY 1
         * START WITH 100
         * MAXVALUE 999999999
         * NOCYCLE
         * NOCACHE;
         */
        String sequenceDdl = "CREATE SEQUENCE T_MESSAGE_ID_SEQ\n" +
                "INCREMENT BY 1\n" +
                "START WITH 1\n" +
                "MAXVALUE 999999999\n" +
                "NOCYCLE\n" +
                "NOCACHE";
        /**
         * create table t_message
         * (
         *     id    int          not null primary key,
         *     name  char(20)     not null,
         *     email char(40),
         *     msg   varchar(256) not null
         * );
         */
        String createTableDdl = "create table t_message\n" +
                "(\n" +
                "    id    int          not null primary key,\n" +
                "    name  varchar(20)     not null,\n" +
                "    email varchar(40),\n" +
                "    msg   varchar(256) not null\n" +
                ")";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            DatabaseMetaData metaData = connection.getMetaData();
            boolean supportsResultSetTypeScrollSensitive = metaData.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE);
            System.out.printf("supportsResultSetTypeScrollSensitive = %b%n", supportsResultSetTypeScrollSensitive);

            boolean supportsResultSetTypeScrollInSensitive = metaData.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE);
            System.out.printf("supportsResultSetTypeScrollInSensitive = %b%n", supportsResultSetTypeScrollInSensitive);

            boolean supportsResultSetConcurrency = metaData.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            System.out.printf("supportsResultSetConcurrency = %b%n", supportsResultSetConcurrency);


            System.out.printf("statementFetchSize = %d%n", statement.getFetchSize());
            statement.setFetchSize(1);
            System.out.printf("statementFetchSize = %d%n", statement.getFetchSize());

//            if (statement.execute(sequenceDdl)) {
//                System.out.println("execute sequenceDdl return true");
//            } else {
//                int updateCount = statement.getUpdateCount();
//                System.out.printf("sequenceDdl updateCount = %d\n", updateCount);
//            }
//
//            if (statement.execute(createTableDdl)) {
//                System.out.println("execute createTableDdl return true");
//            } else {
//                int updateCount = statement.getUpdateCount();
//                System.out.printf("createTableDdl updateCount = %d\n", updateCount);
//            }

//            if (statement.execute(insert)) {
//                System.out.println("execute insert return true");
//            } else {
//                int updateCount = statement.getUpdateCount();
//                System.out.printf("insert updateCount = %d\n", updateCount);
//            }

            try (ResultSet resultSet = statement.executeQuery(querySql)) {

                System.out.printf("resultSetFetchSize = %d%n", resultSet.getFetchSize());
                resultSet.setFetchSize(1);
                System.out.printf("resultSetFetchSize = %d%n", resultSet.getFetchSize());

                /**
                 * query
                 */
                while (resultSet.next()) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String msg = resultSet.getString("msg");
                    System.out.printf("id = %d\t name = %s\t email = %s\t msg = %s\n", id, name, email, msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
