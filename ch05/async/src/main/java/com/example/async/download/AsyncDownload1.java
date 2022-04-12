package com.example.async.download;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet(urlPatterns = "/asyncDownload1", asyncSupported = true)
public class AsyncDownload1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.printf("AsyncDownload1 # doGet thread id = %s%n", Thread.currentThread().getId());
        AsyncContext asyncContext = req.startAsync();

        ServletOutputStream outputStream = resp.getOutputStream();
        Path path = Paths.get("D:\\common\\temp\\pdf.docx");
        String mimeType = getServletContext().getMimeType(path.toString());
        resp.setContentType(mimeType);
        outputStream.setWriteListener(new WriteListener() {

            final FileInputStream fileInputStream = new FileInputStream(path.toFile());

            @Override
            public void onWritePossible() {
                System.out.printf("onWritePossible thread id = %s%n", Thread.currentThread().getId());
                byte[] buffer = new byte[1024];
                int length = 0;
                try {
                    while (outputStream.isReady() && (length = fileInputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, length);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (length == -1) {
                        try {
                            fileInputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.printf("complete thread id = %s%n", Thread.currentThread().getId());
                        asyncContext.complete();
                    }
                }
            }

            @Override
            public void onError(Throwable t) {
                System.out.printf("onError thread id = %s%n", Thread.currentThread().getId());
                try {
                    fileInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                throw new RuntimeException(t);
            }
        });


    }
}
