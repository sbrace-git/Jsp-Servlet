package com.example.onlineuser.session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/login")
public class Login extends HttpServlet {

    private final static Map<String, String> users;

    static {
        users = new HashMap<>();
        users.put("caterpillar", "123456");
        users.put("momor", "98765");
        users.put("hamimi", "13579");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (users.containsKey(username) && users.get(username).equals(password)) {
            HttpSession session = req.getSession(false);
            if (null != session) {
                req.changeSessionId();
            } else {
                session = req.getSession();
            }
            session.setAttribute("username", username);
        }
        // TODO: 2022/3/23 换成html
        resp.sendRedirect("welcome.view");
//        resp.getWriter().println("login error");
    }
}
