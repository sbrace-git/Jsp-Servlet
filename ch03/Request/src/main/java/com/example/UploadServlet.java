package com.example;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/upload")
public class UploadServlet extends HttpServlet {

    private final Pattern fileNameRegex = Pattern.compile("filename=\"(.*)\"");
    private final Pattern fileRangeRegex = Pattern.compile("filename=\".*\"\\r\\n.*\\r\\n\\r\\n(.*+)");

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        byte[] content = bodyContent(req);
        String contentAsTxt = new String(content, StandardCharsets.ISO_8859_1);

        String filename = filename(contentAsTxt);
        Range fileRange = fileRange(contentAsTxt, req.getContentType());

        write(  content,
                contentAsTxt.substring(0, fileRange.start)
                        .getBytes(StandardCharsets.ISO_8859_1)
                        .length,
                contentAsTxt.substring(0, fileRange.end)
                        .getBytes(StandardCharsets.ISO_8859_1)
                        .length,
                String.format("D:\\common\\temp\\%s", filename)
        );

    }

    // 读取请求内容
    private byte[] bodyContent(HttpServletRequest request) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ServletInputStream in = request.getInputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
            return out.toByteArray();
        }
    }

    //获取文件名
    private String filename(String contentTxt) {
        Matcher matcher = fileNameRegex.matcher(contentTxt);
        matcher.find();

        String filename = matcher.group(1);
        if (filename.contains("\\")) {
            return filename.substring(filename.lastIndexOf("\\") + 1);
        }
        return filename;
    }

    // 获取文件边界范围
    private Range fileRange(String content, String contentType) {
        Matcher matcher = fileRangeRegex.matcher(content);
        matcher.find();
        int start = matcher.start(1);
        String boundary = contentType.substring(contentType.lastIndexOf("=") + 1, contentType.length());
        int end = content.indexOf(boundary, start) - 4;
        return new Range(start, end);
    }

    //储存文件内容
    private void write(byte[] content, int start, int end, String file) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(content, start, (end - start));
        }
    }

    private static class Range {
        final int start;
        final int end;

        public Range(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
