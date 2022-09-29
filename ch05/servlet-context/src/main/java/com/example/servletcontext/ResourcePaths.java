package com.example.servletcontext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/resourcePaths")
public class ResourcePaths extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        getServletContext().getResourcePaths("/").forEach(writer::println);
        /**
         * /META-INF/
         * /index.jsp
         * /WEB-INF/
         * /avatar/
         */
    }
}
