package com.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.UUID;

//@WebServlet("/uploads")
//@MultipartConfig(
//        fileSizeThreshold = 1024
//        location = "D:\\common\\temp\\"
//)
public class UploadsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        req.getParts().stream().filter(part -> part.getName().startsWith("file")).forEach(this::write);
    }

    private void write(Part part) {
        String submittedFileName = part.getSubmittedFileName();
        String ext = submittedFileName.substring(submittedFileName.lastIndexOf("."));
        try {
            part.write(String.format("%s%s%s",
                    Instant.now().toEpochMilli(),
                    UUID.randomUUID().toString().replace("-", ""),
                    ext));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
