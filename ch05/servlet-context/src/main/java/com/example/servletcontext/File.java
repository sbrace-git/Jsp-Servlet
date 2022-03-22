package com.example.servletcontext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/file")
public class File extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        String file = "D:\\common\\temp\\jpg.jpg";
        // File FileReader FileInputStream
        // 相对路径 指向启动 web 容器时的命令执行目录
        String file = "catalina.sh";
        Path filePath = Paths.get(file);
        System.out.printf("filePath = %s%n", filePath);

        String mimeType = getServletContext().getMimeType(file);
        System.out.printf("mimeType = %s%n", mimeType);
        resp.setContentType(mimeType);

        Path fileName = filePath.getFileName();
        System.out.printf("fileName = %s%n", fileName);
        resp.setHeader("content-disposition", "attachment; filename=" + fileName);

        Files.copy(filePath, resp.getOutputStream());
    }
}
