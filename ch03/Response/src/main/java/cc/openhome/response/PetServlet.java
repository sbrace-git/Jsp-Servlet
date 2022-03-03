package cc.openhome.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@WebServlet("/pet")
public class PetServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("<body>");
        writer.printf("联系人：<a href='mailto:%s'>%s</a>%n",
                req.getParameter("email"),
                req.getParameter("user"));
        writer.println("<br/>最喜欢的宠物类型");
        writer.println("<ul>");
        Arrays.asList(req.getParameterValues("type"))
                .forEach(type -> writer.printf("<li>%s</li>%n", type));
        writer.println("</ul>");
        writer.println("</body>");
        writer.println("</html>");

    }
}
