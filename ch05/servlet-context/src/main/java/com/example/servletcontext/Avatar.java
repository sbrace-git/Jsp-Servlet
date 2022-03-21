package com.example.servletcontext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
        urlPatterns = "/avatar",
        initParams = {
                @WebInitParam(name = "AVATAR_DIR", value = "/avatar/")
        }
)
public class Avatar extends HttpServlet {

    private static String AVATAR_DIR;

    @Override
    public void init() throws ServletException {
        AVATAR_DIR = getInitParameter("AVATAR_DIR");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>avatar</title>");
        writer.println("</head>");
        writer.println("<body>");

        getServletContext().getResourcePaths(AVATAR_DIR).forEach(image ->
                writer.printf("<img src='%s'>%n", image.replaceFirst("/", "")));

        writer.println("</body>");
        writer.println("</html>");
        getServletContext().getResourcePaths(AVATAR_DIR).forEach(System.out::println);
    }
}
