package com.example.ifmodifiedsince;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet(value = "/getTextFile", loadOnStartup = 0)
public class IfModifiedSinceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain; charset=UTF-8");
        Files.copy(getTextFilePath(), resp.getOutputStream());
    }

    @Override
    protected long getLastModified(HttpServletRequest req) {
        try {
            return Files.getLastModifiedTime(getTextFilePath()).toMillis();
        } catch (IOException e) {
            e.printStackTrace();
            return -1L;
        }
    }

    private Path getTextFilePath() {
        return Paths.get(getServletContext().getRealPath("/file/text_file.txt"));
    }
}
