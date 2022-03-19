package com.example.servletconfig;

import java.io.*;
import java.util.Collections;
import java.util.Enumeration;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet",
        initParams = {
                @WebInitParam(name = "message", value = "你好!"),
                @WebInitParam(name = "param", value = "这是内容")
        })
public class HelloServlet extends HttpServlet {
    private String message;
    private String text;

    public void init() {
        System.out.printf("%s init%n", getClass().getName());
        text = getInitParameter("param");
        message = getInitParameter("message");
        Collections.list(getInitParameterNames()).forEach(initParameterName -> {
            System.out.printf("%s = %s%n", initParameterName, getInitParameter(initParameterName));
        });
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("<h1>" + text + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}