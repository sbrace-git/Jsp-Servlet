package com.example;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/postBody")
public class PostBody extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("<body>");
        writer.println(bodyContent(req.getReader()));
        writer.println("</body>");
        writer.println("</html>");

    }

    private String bodyContent(BufferedReader reader) throws IOException {
        String input;
        StringBuilder requestBody = new StringBuilder();
        while ((input = reader.readLine()) != null) {
            requestBody.append(input).append("</br>");
        }
        return requestBody.toString();
    }
}
