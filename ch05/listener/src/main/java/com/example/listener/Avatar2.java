package com.example.listener;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;

public class Avatar2 extends HttpServlet {

    private final Path AVATAR_DIR;

    @Override
    public void init() throws ServletException {
        System.out.println("Avatar2 init");
    }

    public Avatar2(Path avatarDir) {
        System.out.println("Avatar2 constructor");
        this.AVATAR_DIR = avatarDir;
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

        String avatarPath = String.format("/%s", AVATAR_DIR.getFileName());
        getServletContext().getResourcePaths(avatarPath).forEach(image ->
                writer.printf("<img src='%s'>%n", image.replaceFirst("/", "")));

        writer.println("</body>");
        writer.println("</html>");
        getServletContext().getResourcePaths(avatarPath).forEach(System.out::println);
    }
}
