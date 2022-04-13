package cc.openhome.gossip.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

@WebServlet("/new_message")
public class NewMessage extends HttpServlet {

    private static final String USER = "D:\\common\\temp\\users";
    private static final String LOGIN_PATH = "index.html";
    private static final String MEMBER_PATH = "member";
    private static final String MEMBER_VIEW = "member.view";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = (String) req.getSession().getAttribute("login");
        if (null == username) {
            resp.sendRedirect(LOGIN_PATH);
        }
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String blabla = req.getParameter("blabla");
        if (null == blabla || blabla.length() > 140 || blabla.length() == 0) {
            req.getRequestDispatcher(MEMBER_VIEW).forward(req,resp);
            return;
        }
        Path path = Paths.get(USER, username,
                String.format("%s.txt", Instant.now().toEpochMilli()));
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            bufferedWriter.write(blabla);
        }
        resp.sendRedirect(MEMBER_PATH);
    }
}
