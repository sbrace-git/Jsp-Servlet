package cc.openhome.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/header")
public class ResponseHeaderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("header-name-1","header-value-1");
        resp.setHeader("header-name-2","header-value-2");
        resp.setHeader("header-name-2","header-value-3");
        // add header
        resp.addHeader("header-name-2","header-value-4");
        // set header
//        resp.setHeader("header-name-2","header-value-5");
    }
}
