package cc.openhome.match;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("*.ext")
public class Extension extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.printf("requestURL = %s%n", req.getRequestURL());
        System.out.printf("pathInfo = %s%n", req.getPathInfo());
        System.out.printf("servletPath = %s%n", req.getServletPath());
        System.out.printf("servletName = %s%n", getServletName());
        HttpServletMapping httpServletMapping = req.getHttpServletMapping();
        System.out.printf("MappingMatch = %s%n", httpServletMapping.getMappingMatch());
        System.out.printf("MatchValue = %s%n", httpServletMapping.getMatchValue());
        System.out.printf("Pattern = %s%n", httpServletMapping.getPattern());
    }
}
