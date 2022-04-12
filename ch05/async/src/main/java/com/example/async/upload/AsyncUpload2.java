package com.example.async.upload;

import javax.servlet.AsyncContext;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@MultipartConfig
@WebServlet(urlPatterns = "/asyncUpload2", asyncSupported = true)
public class AsyncUpload2 extends HttpServlet {
    private final Pattern fileNameRegex =
            Pattern.compile("filename=\"(.*)\"");

    private final Pattern fileRangeRegex =
            Pattern.compile("filename=\".*\"\\r\\n.*\\r\\n\\r\\n(.*+)");

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.printf("doPost thread id = %s%n", Thread.currentThread().getName());
        AsyncContext ctx = request.startAsync();
        ctx.setTimeout(300000L);

        ServletInputStream in = request.getInputStream();

        in.setReadListener(new ReadListener() {
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            String submittedFileName;

            @Override
            public void onDataAvailable() throws IOException {
                System.out.printf("onDataAvailable thread id = %s%n", Thread.currentThread().getName());
//                try {
//                    Thread.sleep(2000);
//                    Part photo = request.getPart("photo");
//                    InputStream inputStream = photo.getInputStream();
//                    submittedFileName = photo.getSubmittedFileName();
//                    byte[] buffer = new byte[1024];
//                    int length;
//                    while ((length = inputStream.read(buffer)) != -1) {
//                        out.write(buffer, 0, length);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    throw new RuntimeException(e);
//                }

                byte[] buffer = new byte[1024];
                int length = -1;
                while (in.isReady() && (length = in.read(buffer)) != -1) {
                    out.write(buffer, 0, length);
                }
            }

            @Override
            public void onAllDataRead() throws IOException {
                byte[] content = out.toByteArray();
                String contentAsTxt = new String(content, "ISO-8859-1");

                String filename = filename(contentAsTxt);
                Range fileRange = fileRange(contentAsTxt, request.getContentType());
                write(content,
                        contentAsTxt.substring(0, fileRange.start)
                                .getBytes("ISO-8859-1")
                                .length,
                        contentAsTxt.substring(0, fileRange.end)
                                .getBytes("ISO-8859-1")
                                .length,
                        String.format("D:\\common\\temp\\test\\%s", filename)
                );
//                System.out.printf("onAllDataRead thread id = %s%n", Thread.currentThread().getName());
//                FileOutputStream fileOutputStream = new FileOutputStream("D:\\common\\temp\\test\\" + submittedFileName);
//                fileOutputStream.write(out.toByteArray());
                response.getWriter().println("Upload Successfully");
                ctx.complete();
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.printf("onError thread id = %s%n", Thread.currentThread().getName());
                ctx.complete();
                throw new RuntimeException(throwable);
            }
        });
    }

    // 取得檔案名稱
    private String filename(String contentTxt)
            throws UnsupportedEncodingException {
        Matcher matcher = fileNameRegex.matcher(contentTxt);
        matcher.find();

        String filename = matcher.group(1);
        // 如果名稱上包含資料夾符號「\」，就只取得最後的檔名
        if (filename.contains("\\")) {
            return filename.substring(filename.lastIndexOf("\\") + 1);
        }
        return filename;
    }

    // 取得檔案邊界範圍
    private Range fileRange(String content, String contentType) {
        Matcher matcher = fileRangeRegex.matcher(content);
        matcher.find();
        int start = matcher.start(1);

        String boundary = contentType.substring(
                contentType.lastIndexOf("=") + 1, contentType.length());
        int end = content.indexOf(boundary, start) - 4;

        return new Range(start, end);
    }

    // 儲存檔案內容
    private void write(byte[] content, int start, int end, String file)
            throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(content, start, (end - start));
        }
    }

    // 封裝範圍起始與結束
    private static class Range {
        final int start;
        final int end;

        public Range(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

}
