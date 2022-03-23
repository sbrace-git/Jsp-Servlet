package com.example.onlineuser.session;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/welcome.view")
public class Welcome extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain; charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        HttpSession session = req.getSession(false);
        if (null == session) {
            writer.println("未登录");
        } else {
            writer.printf("welcome %s%n", session.getAttribute("username"));
        }
        writer.printf("online user count = %d", LoginSessionListener.getCounter());
    }
}
