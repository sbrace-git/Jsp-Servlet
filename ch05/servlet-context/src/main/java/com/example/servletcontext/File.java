package com.example.servletcontext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet("/file")
public class File extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/octet-stream; filename='jpg.jpg'");
        Files.copy(Paths.get("D:\\common\\temp\\jpg.jpg"),resp.getOutputStream());
    }
}
