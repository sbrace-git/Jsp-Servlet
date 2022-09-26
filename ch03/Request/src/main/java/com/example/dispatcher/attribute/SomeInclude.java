package com.example.dispatcher.attribute;

import com.example.dispatcher.Book;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/someInclude-attribute")
public class SomeInclude extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SomeInclude.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();
        writer.println("Some do one...");

        List<Book> books = Arrays.asList(
                new Book("《JSP & Servlet学习笔记（第3版）》", "林信良"),
                new Book("《Java学习笔记》", "林信良")
        );
        req.setAttribute("books", books);

        RequestDispatcher dispatcher = req.getRequestDispatcher("other-attribute");

        dispatcher.include(req, resp);

        writer.println("Some do two...");

        Collections.list(req.getAttributeNames()).forEach(attributeName ->
                logger.log(Level.INFO, "{0} = {1}", new Object[]{attributeName, req.getAttribute(attributeName)}));
    }
}
