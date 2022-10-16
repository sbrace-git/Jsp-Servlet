package com.example.fileservice.controller;

import com.example.fileservice.service.FileService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete")
public class Delete extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FileService fileService = (FileService) getServletContext().getAttribute("fileService");
        int id = Integer.parseInt(req.getParameter("id"));
        fileService.delete(id);
        resp.sendRedirect("file.jsp");
    }
}
