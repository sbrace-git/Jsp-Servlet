package cc.openhome;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet(name = "attack", value = "/attack")
public class AttackServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String name = Optional.of(request.getParameter("name"))
                .map(nameTemp -> nameTemp.replace("<", "&lt;")
                                         .replace(">", "&gt;"))
                .orElse("Guest");
        PrintWriter writer = response.getWriter();
        writer.print("<!DOCTYPE html>");
        writer.print("<html>");
        writer.print("<head>");
        writer.print("<title>Hello</title>");
        writer.print("</head>");
        writer.print("<body>");
        writer.printf("<h1> Hello! %s!</h1>", name);
        writer.print("</body>");
        writer.print("</html>");
    }
}
