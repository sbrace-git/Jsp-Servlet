package com.example.onlineuser.view;

import com.example.onlineuser.listener.LoginSessionListener;
import com.example.onlineuser.model.LoginUser;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/welcome.view")
public class Welcome extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        String username = Optional.ofNullable(session)
                .map(httpSession -> (LoginUser) httpSession.getAttribute("loginUser"))
                .map(LoginUser::getName).orElse(null);

        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<meta charset='UTF-8'>");
        writer.println("<title>welcome</title>");
        writer.println("</head>");
        writer.println("<body>");

        if (null == username) {
            writer.println("未登录");
        } else {
            writer.printf("welcome %s<br>", username);
            writer.println("<a href='logout'>logout</a><br>");
        }
        writer.printf("online user count = %d<br>", LoginSessionListener.getCounter());
        writer.println("username\tsessionId\tlastAccessedTime\tuserAgent<br>");
        LoginSessionListener.getAllLoginUser().forEach(loginUser -> writer.printf("%s<br>", loginUser.showDetail()));

        writer.println("</body>");
        writer.println("</html>");
    }
}
