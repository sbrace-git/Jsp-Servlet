package com.example.servletcontext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/push")
public class Push extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional.ofNullable(req.newPushBuilder())
                .ifPresent(pushBuilder -> {
                    // HTTP 2.0 supported
                    System.out.println("pushBuilder push");
                    pushBuilder.path("avatar/caterpillar.jpg")
                            .addHeader("Content-Type", "image/jpg")
                            .push();
                });

        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<meta charset='UTF-8'>");
        writer.println("<title>push</title>");
        writer.println("</head>");
        writer.println("<body>");
        writer.println("<img src='avatar/caterpillar.jpg'>");
        writer.println("</body>");
        writer.println("</html>");

    }
}
