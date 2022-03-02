package cc.openhome.response.charset;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@WebServlet("/iso88591CharSet")
public class Iso88591CharSet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.printf("ContentType = %s%n", resp.getContentType());
        System.out.printf("CharacterEncoding = %s%n", resp.getCharacterEncoding());
        resp.getWriter().println(new String("Hello world! 你好，世界！".getBytes(),StandardCharsets.ISO_8859_1));
    }
}