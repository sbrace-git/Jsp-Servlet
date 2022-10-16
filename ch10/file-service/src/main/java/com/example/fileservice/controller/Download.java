package com.example.fileservice.controller;

import com.example.fileservice.entity.Image;
import com.example.fileservice.service.FileService;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/download")
public class Download extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FileService fileService = (FileService) getServletContext().getAttribute("fileService");
        Image image = fileService.getFile(Integer.parseInt(req.getParameter("id")));
        String fileName = image.getFileName();
        fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
        resp.setContentType("application/octet-steam");
        resp.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");

        byte[] bytes = image.getBytes();
        ServletOutputStream outputStream = resp.getOutputStream();
        outputStream.write(bytes);
    }
}
