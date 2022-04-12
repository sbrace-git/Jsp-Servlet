package com.example.async.download;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

@WebServlet(urlPatterns = "/asyncDownload", asyncSupported = true)
public class AsyncDownload extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.printf("AsyncDownload # doGet thread id = %s%n", Thread.currentThread().getId());

        AsyncContext asyncContext = req.startAsync();
        CompletableFuture.runAsync(() -> {
            System.out.printf("runAsync thread id = %s%n", Thread.currentThread().getId());
            Path path = Paths.get("D:\\common\\temp\\pdf.docx");
            String mimeType = getServletContext().getMimeType(path.toString());
            resp.setContentType(mimeType);
            try {
                Files.copy(path,resp.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                System.out.printf("runAsync complete thread id = %s%n", Thread.currentThread().getId());
                asyncContext.complete();
            }
        });
    }
}
