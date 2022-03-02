package cc.openhome.response.charset;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/utf8CharSet")
public class Utf8CharSet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain; charset=UTF-8");

        System.out.printf("ContentType = %s%n", resp.getContentType());
        System.out.printf("CharacterEncoding = %s%n", resp.getCharacterEncoding());
        resp.getWriter().println("Hello world! 你好，世界！");
    }
}
