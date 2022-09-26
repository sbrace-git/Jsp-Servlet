package com.example.dispatcher.attribute;

import com.example.dispatcher.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/other-attribute")
public class Other extends HttpServlet {

    private static final Logger logger = Logger.getLogger(Other.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Book> otherBooks = Arrays.asList(
                new Book("《JSP & Servlet学习笔记（第3版）》-other", "林信良-other"),
                new Book("《Java学习笔记》-other", "林信良-other")
        );
        req.setAttribute("otherBooks", otherBooks);

        Collections.list(req.getAttributeNames()).forEach(attributeName ->
                logger.log(Level.INFO, "{0} = {1}", new Object[]{attributeName, req.getAttribute(attributeName)}));

        resp.getWriter().println("Other do one...");
    }
}
