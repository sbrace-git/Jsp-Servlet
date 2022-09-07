package com.example.jdbcmetadata;

import java.sql.*;

public class UpdateResultSet {
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
             Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)) {
            boolean supportsResultSetConcurrency = connection.getMetaData().supportsResultSetConcurrency(
                    ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            System.out.printf("supportsResultSetConcurrency = %b%n", supportsResultSetConcurrency);

            try (ResultSet resultSet = statement.executeQuery(querySql)) {
                /**
                 * insert
                 */
                resultSet.moveToInsertRow();
                resultSet.updateString("name","insert_name");
                resultSet.updateString("email","insert_email");
                resultSet.updateString("msg","insert_msg");
                resultSet.insertRow();
                resultSet.moveToCurrentRow();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String msg = resultSet.getString("msg");
                    System.out.printf("id = %d\t", id);
                    System.out.printf("name = %s\t", name);
                    System.out.printf("email = %s\t", email);
                    System.out.printf("msg = %s\n", msg);
                    /**
                     * update
                     */
                    if (6 == id) {
                        resultSet.updateString("email", "new_email");
                        resultSet.updateRow();
                    }
                    /**
                     * delete
                     */
                    if (3 == id) {
                        resultSet.deleteRow();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
