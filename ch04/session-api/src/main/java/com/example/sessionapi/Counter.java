package com.example.sessionapi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/counter")
public class Counter extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        Integer count = Optional.ofNullable(req.getSession().getAttribute("count"))
                .map(attr -> (Integer) attr + 1)
                .orElse(0);

        req.getSession().setAttribute("count", count);

        PrintWriter writer = resp.getWriter();
        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>count</title>");
        writer.println("</head>");
        writer.println("<body>");

        writer.printf("<h1>count = %d</h1>%n", count);
        writer.printf("<a href='%s'>递增</a>%n",resp.encodeURL("counter"));

        writer.println("</body>");
        writer.println("</html>");

    }
}
