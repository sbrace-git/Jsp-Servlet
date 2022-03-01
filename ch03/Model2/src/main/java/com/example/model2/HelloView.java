package com.example.model2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/hello.view")
public class HelloView extends HttpServlet {

    private final String htmlTemplate = "<!DOCTYPE html>" +
            "<html>" +
            "   <head>" +
            "       <meta charset='UTF-8'/>" +
            "       <title>%s</title>" +
            "   </head>" +
            "   <body>" +
            "       <h1>%s</h1>" +
            "   </body>" +
            "</html>";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("user");
        String message = (String) req.getAttribute("message");
        String html = String.format(htmlTemplate, user, message);
        resp.setContentType("text/html; charset=utf-8");
        resp.getWriter().print(html);
    }
}
