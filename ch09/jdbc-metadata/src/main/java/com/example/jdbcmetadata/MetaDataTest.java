package com.example.jdbcmetadata;

import org.h2.expression.function.SoundexFunction;

import java.sql.*;

public class MetaDataTest {
    public static void main(String[] args) {
        try {
//            Class.forName("org.h2.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        String url = "jdbc:h2:tcp://localhost:9092/D:/server/H2/data/test";
//        String user = "sa";
//        String password = "";

        String url = "jdbc:mysql://localhost:3307/ch_09?useCursorFetch=true";
        String user = "root";
        String password = "1111";

        String querySql = "select id id_label,name,email from t_message";
        String insert = "insert into t_message(name,email,msg) values ('justin','justin@mail.com','message...')";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            String databaseProductName = databaseMetaData.getDatabaseProductName();
            System.out.println("databaseProductName = " + databaseProductName);
            ResultSet tMessageColumnsResult = databaseMetaData.getColumns(null, null, "t_message", null);
            while (tMessageColumnsResult.next()) {
                String columnName = tMessageColumnsResult.getString("COLUMN_NAME");
                System.out.printf("columnName = %s\t", columnName);
                String typeName = tMessageColumnsResult.getString("TYPE_NAME");
                System.out.printf("typeName = %s\t", typeName);
                int columnSize = tMessageColumnsResult.getInt("COLUMN_SIZE");
                System.out.printf("columnSize = %d\t", columnSize);
                boolean isNullable = tMessageColumnsResult.getBoolean("IS_NULLABLE");
                System.out.printf("isNullable = %b\t", isNullable);
                String columnDef = tMessageColumnsResult.getString("COLUMN_DEF");
                System.out.printf("columnDef = %s%n", columnDef);
            }

            ResultSet resultSet = statement.executeQuery(querySql);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            System.out.println("columnCount = " + columnCount);
            String columnName = resultSetMetaData.getColumnName(1);
            System.out.println("columnName = " + columnName);
            String columnLabel = resultSetMetaData.getColumnLabel(1);
            System.out.println("columnLabel = " + columnLabel);
            int columnType = resultSetMetaData.getColumnType(1);
            System.out.println("columnType = " + columnType);
            String columnClassName = resultSetMetaData.getColumnClassName(1);
            System.out.println("columnClassName = " + columnClassName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
