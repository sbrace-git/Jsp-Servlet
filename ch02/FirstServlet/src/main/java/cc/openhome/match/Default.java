package cc.openhome.match;

import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class Default extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("requestURL", req.getRequestURL());
        jsonObject.put("pathInfo", req.getPathInfo());
        jsonObject.put("servletPath", req.getServletPath());
        jsonObject.put("servletName", getServletName());

        HttpServletMapping httpServletMapping = req.getHttpServletMapping();
        jsonObject.put("mappingMatch", httpServletMapping.getMappingMatch());
        jsonObject.put("matchValue", httpServletMapping.getMatchValue());
        jsonObject.put("pattern", httpServletMapping.getPattern());

        resp.setContentType("text/json; charset=UTF-8");
        resp.getWriter().print(jsonObject);
    }
}
