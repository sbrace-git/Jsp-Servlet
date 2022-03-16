package cc.openhome.gossip.view;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/register_success.view")
public class RegisterSuccess extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<meta charset='UtF-8'>");
        writer.println("<title>会员注册成功</title>");
        writer.println("</head>");
        writer.println("<body>");
        writer.printf("<h1>%s 会员注册成功</h1>",req.getParameter("username"));
        writer.println("<a href='index.html'>回首页</a>");
        writer.println("</body>");
        writer.println("</html>");
    }
}
