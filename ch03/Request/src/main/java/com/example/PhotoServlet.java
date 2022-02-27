package com.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@MultipartConfig
@WebServlet("/photo")
public class PhotoServlet extends HttpServlet {
    private final Pattern fileNameRegex = Pattern.compile("filename=\"(.*)\"");

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String name = req.getParameter("name");
        System.out.printf("name = %s%n", name);

        String passwd = req.getParameter("passwd");
        System.out.printf("passwd = %s%n", passwd);


        Part photo = req.getPart("photo");
        String filename = getSubmittedFileName(photo);
        write(photo, filename);
    }

    private void write(Part photo, String filename) throws IOException {

        try (InputStream in = photo.getInputStream();
             FileOutputStream out = new FileOutputStream(String.format("D:\\common\\temp\\%s", filename))) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
        }
    }

    private String getSubmittedFileName(Part part) {
        String header = part.getHeader("Content-Disposition");
        System.out.println(header);
        Matcher matcher = fileNameRegex.matcher(header);
        matcher.find();

        String name = part.getName();
        System.out.printf("name = %s%n", name);

        String submittedFileName = part.getSubmittedFileName();
        System.out.printf("submittedFileName = %s%n", submittedFileName);

        long size = part.getSize();
        System.out.printf("size = %d Bytes%n", size);

        String filename = matcher.group(1);
        if (filename.contains("\\")) {
            return filename.substring(filename.lastIndexOf("\\") + 1);
        }
        return filename;
    }
}
