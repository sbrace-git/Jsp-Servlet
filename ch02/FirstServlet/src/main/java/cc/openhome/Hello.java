package cc.openhome;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello")
public class Hello extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String name = request.getParameter("name");
        PrintWriter writer = response.getWriter();
        writer.print("<!DOCTYPE html>");
        writer.print("<html>");
        writer.print("<head>");
        writer.print("<title>Hello</title>");
        writer.print("</head>");
        writer.print("<body>");
        writer.printf("<h1> Hello! %s! %n</h1>", name);
        writer.print("</body>");
        writer.print("</html>");
    }
}
