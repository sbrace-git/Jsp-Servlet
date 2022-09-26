package com.example.dispatcher;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/someForward")
public class SomeForward extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.println("Some do one...");
        // writer.flush();
        // java.lang.IllegalStateException

        RequestDispatcher dispatcher = req.getRequestDispatcher("other");

        dispatcher.forward(req, resp);

        writer.println("Some do two...");
    }
}