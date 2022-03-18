package cc.openhome.gossip.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet("/del_message")
public class DeleteMessage extends HttpServlet {

    private static final String USER = "D:\\common\\temp\\users";
    private static final String LOGIN_PATH = "index.html";
    private static final String MEMBER_PATH = "member";
    private static final String MEMBER_VIEW = "member.view";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("login");
        if (null == username) {
            resp.sendRedirect(LOGIN_PATH);
            return;
        }
        String millis = req.getParameter("millis");
        if (null != millis) {
            Files.delete(Paths.get(USER,username,String.format("%s.txt",millis)));
        }
        resp.sendRedirect(MEMBER_PATH);
    }
}
