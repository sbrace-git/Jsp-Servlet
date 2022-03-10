package com.example.session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.stream.IntStream;

@WebServlet("/search")
public class Search extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = resp.getWriter();

        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>search</title>");
        writer.println("</head>");
        writer.println("<body>");
        results(writer);
        pages(req, writer);
        writer.println("</body>");
        writer.println("</html>");
    }

    private void results(PrintWriter writer) {
        writer.println("<ul>");
        IntStream.rangeClosed(1, 10)
                .forEach(i -> writer.printf("<li>搜索结果%d</li>%n", i));
        writer.println("</ul>");
    }

    private void pages(HttpServletRequest req, PrintWriter writer) {
        int page = Optional.ofNullable(req.getParameter("page"))
                .map(Integer::valueOf)
                .orElse(1);
        IntStream.rangeClosed(1, 10)
                .forEach(i -> {
                    if (page == i) {
                        writer.println(i);
                    } else {
                        writer.printf("<a href='search?page=%d'>%d</a>%n", i, i);
                    }
                });
    }
}
