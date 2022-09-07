package com.example.jdbcmetadata;

import java.sql.*;

public class TFilesInfo {
    public static void main(String[] args) {
        try {
            Class.forName("org.h2.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // java -jar h2-2.1.214.jar -webAllowOthers -tcpAllowOthers
        // -tcpPort change port
        // Web Console server running at http://a.b.c.d:8082 (others can connect)
        // TCP server running at tcp://a.b.c.d:9092 (others can connect)
        // PG server running at pg://a.b.c.d:5435 (only local connections)
        String url = "jdbc:h2:tcp://localhost:9092/D:/server/H2/data/test";
        String user = "sa";
        String password = "";
        String querySql = "select * from t_message";
        String insert = "insert into t_message(name,email,msg) values ('justin','justin@mail.com','message...')";
        String ddl = "create table t_message\n" +
                "(\n" +
                "    id    int          not null auto_increment primary key,\n" +
                "    name  varchar(20)     not null,\n" +
                "    email varchar(40),\n" +
                "    msg   varchar(256) not null\n" +
                ");";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

            DatabaseMetaData metaData = connection.getMetaData();
            boolean supportsResultSetTypeScrollSensitive = metaData.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE);
            System.out.printf("supportsResultSetTypeScrollSensitive = %b%n", supportsResultSetTypeScrollSensitive);

            boolean supportsResultSetTypeScrollInSensitive = metaData.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE);
            System.out.printf("supportsResultSetTypeScrollInSensitive = %b%n", supportsResultSetTypeScrollInSensitive);

            boolean supportsResultSetConcurrency = metaData.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            System.out.printf("supportsResultSetConcurrency = %b%n", supportsResultSetConcurrency);

//            if (statement.execute(insert)) {
//                System.out.println("execute return true");
//            } else {
//                int updateCount = statement.getUpdateCount();
//                System.out.printf("updateCount = %d\n", updateCount);
//            }

            try (ResultSet resultSet = statement.executeQuery(querySql)) {
                /**
                 * query
                 */
//                    while (resultSet.next()) {
//                        try {
//                            Thread.sleep(1000);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        int id = resultSet.getInt("id");
//                        String name = resultSet.getString("name");
//                        String email = resultSet.getString("email");
//                        String msg = resultSet.getString("msg");
//                        System.out.printf("id = %d\t", id);
//                        System.out.printf("name = %s\t", name);
//                        System.out.printf("email = %s\t", email);
//                        System.out.printf("msg = %s\n", msg);
//                    }

                /**
                 * cursor
                 */
//                resultSet.first();
//                int lastRowNumber = resultSet.getRow();
//                System.out.printf("lastRowNumber = %d%n", lastRowNumber);

//                    resultSet.absolute(2);
//                    resultSet.next();
//                    resultSet.relative(-1);

//                boolean first = resultSet.isFirst();
//                System.out.printf("first = %b%n", first);
//                boolean last = resultSet.isLast();
//                System.out.printf("last = %b%n", last);
//                boolean beforeFirst = resultSet.isBeforeFirst();
//                System.out.printf("beforeFirst = %b%n", beforeFirst);
//                boolean afterLast = resultSet.isAfterLast();
//                System.out.printf("afterLast = %b%n", afterLast);
//
//                int id = resultSet.getInt("id");
//                String name = resultSet.getString("name");
//                String email = resultSet.getString("email");
//                String msg = resultSet.getString("msg");
//                System.out.printf("id = %d\t", id);
//                System.out.printf("name = %s\t", name);
//                System.out.printf("email = %s\t", email);
//                System.out.printf("msg = %s\n", msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
