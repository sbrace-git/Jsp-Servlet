package com.example;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@WebServlet("/postBody")
public class PostBodyServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("<body>");
        writer.println(bodyContent(req.getReader()));
        writer.println("</body>");
        writer.println("</html>");

    }

    private String bodyContent(BufferedReader reader) {
        return reader.lines()
                .map(line -> line.getBytes(StandardCharsets.ISO_8859_1))
                .map(bytes -> new String(bytes, StandardCharsets.UTF_8))
                .collect(Collectors.joining("</br>"));
    }
}
