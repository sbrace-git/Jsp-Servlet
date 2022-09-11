package com.example.jdbcmetadata;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import javax.sql.rowset.WebRowSet;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/xmlMessage")
public class XmlMessage extends HttpServlet {

    private WebRowSet webRowSet;

    @Override
    public void init() {
        System.out.println("XmlMessage init");
        try {
            RowSetFactory rowSetFactory = RowSetProvider.newFactory();
            webRowSet = rowSetFactory.createWebRowSet();
            webRowSet.setDataSourceName("java:/comp/env/jdbc/demo");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/xml;charset=UTF-8");
        try {
            webRowSet.setCommand("select * from t_message");
            webRowSet.execute();
            webRowSet.writeXml(resp.getOutputStream());
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
