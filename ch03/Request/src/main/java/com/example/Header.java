package com.example;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

@WebServlet("/header")
public class Header extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = resp.getWriter();

        writer.print("<!DOCTYPE html>");
        writer.print("<html>");
        writer.print("<head>");
        writer.print("<title>");
        writer.print("header test page");
        writer.print("</title>");
        writer.print("</head>");
        writer.print("<body>");
        Collections.list(req.getHeaderNames())
                .forEach(headerName -> writer.printf("%s : %s</br>", headerName, req.getHeader(headerName)));
        writer.print("</body>");
        writer.print("</html>");
    }
}
