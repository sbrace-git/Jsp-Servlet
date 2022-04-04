package com.example.async.upload;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.CompletableFuture;

@MultipartConfig
@WebServlet(urlPatterns = "/supplyAsync", asyncSupported = true)
public class AsyncUpload extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.printf("doPost thread id = %s%n", Thread.currentThread().getId());
        AsyncContext asyncContext = req.startAsync();
        CompletableFuture.runAsync(() -> {
            System.out.printf("runAsync thread id = %s%n", Thread.currentThread().getId());

            HttpServletRequest request = (HttpServletRequest) asyncContext.getRequest();
            try {
                Part photo = request.getPart("photo");
                String submittedFileName = photo.getSubmittedFileName();
                Thread.sleep(3000);
                Files.copy(photo.getInputStream(),
                        Paths.get("D:\\common\\temp\\test\\" + submittedFileName),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException | ServletException | InterruptedException e) {
                e.printStackTrace();
            }
        }).thenRun(() -> {
            System.out.printf("thenRun thread id = %s%n", Thread.currentThread().getId());
            try {
                asyncContext.getResponse().getWriter().println("upload successfully");
                asyncContext.complete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }
}
