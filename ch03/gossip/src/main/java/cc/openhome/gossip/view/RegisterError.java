package cc.openhome.gossip.view;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/register_error.view")
public class RegisterError extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = resp.getWriter();

        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<meta charset='UTF-8'/>");
        writer.println("<title>新增会员失败</title>");
        writer.println("</head>");
        writer.println("<body>");

        writer.println("<h1>新增会员失败</h1>");
        writer.println("<ul style='color: rgb(255, 0, 0);'>");

        List<String> errors = (List<String>) req.getAttribute("errors");
        errors.forEach(error -> writer.printf("<li>%s</li>", error));

        writer.println("</ul>");
        writer.println("<a href='register.html'>返回注册页面</a>");
        writer.println("</body>");
        writer.println("</html>");
    }
}
