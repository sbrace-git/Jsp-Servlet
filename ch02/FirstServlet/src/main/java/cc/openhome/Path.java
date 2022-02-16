package cc.openhome;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/servlet/*")
public class Path extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.print("<!DOCTYPE html>");
        writer.print("<html>");
        writer.print("<head>");
        writer.print("<title>Path Servlet</title>");
        writer.print("</head>");
        writer.print("<body>");
        writer.printf("%s<br/>", req.getRequestURL());
        writer.printf("%s<br/>", req.getContextPath());
        writer.printf("%s<br/>", req.getServletPath());
        writer.printf(req.getPathInfo());
        writer.print("</body>");
        writer.print("</html>");
    }
}
