package com.example.big5;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Set;

@WebServlet("/save")
public class SaveServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String content = req.getParameter("content");
        System.out.println(content);
        String realPath = getServletContext().getRealPath("WEB-INF/file/formContent.txt");
        System.out.println(realPath);
        Path path = Paths.get(realPath);

        Files.readAllLines(path).forEach(System.out::println);
        Files.write(path, Collections.singleton(content));
        Files.readAllLines(path).forEach(System.out::println);

        resp.sendRedirect("form.html");
    }
}
