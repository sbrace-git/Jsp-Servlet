package com.example.fileservice.controller;

import com.example.fileservice.entity.Image;
import com.example.fileservice.service.FileService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@MultipartConfig
@WebServlet("/upload")
public class Upload extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Part part = req.getPart("file");
        String submittedFileName = part.getSubmittedFileName();
        System.out.println("submittedFileName = " + submittedFileName);
        byte[] bytes;
        try (InputStream inputStream = part.getInputStream();
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }
            bytes = byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Image image = new Image();
        image.setBytes(bytes);
        image.setFileName(submittedFileName);
        image.setCreateTime(new Date());
        FileService fileService = (FileService) getServletContext()
                .getAttribute("fileService");
        fileService.save(image);
        resp.sendRedirect("file.jsp");

    }
}
