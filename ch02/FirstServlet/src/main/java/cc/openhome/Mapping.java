package cc.openhome;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/mapping/*")
public class Mapping extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpServletMapping httpServletMapping = req.getHttpServletMapping();
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.print("<!DOCTYPE html>");
        writer.print("<html>");
        writer.print("<head>");
        writer.print("<title>Mapping Servlet</title>");
        writer.print("</head>");
        writer.print("<body>");
        writer.printf("MappingMatch = %s<br/>", httpServletMapping.getMappingMatch());
        writer.printf("MatchValue = %s<br/>", httpServletMapping.getMatchValue());
        writer.printf("Pattern = %s<br/>", httpServletMapping.getPattern());
        writer.print("</body>");
        writer.print("</html>");
    }
}
