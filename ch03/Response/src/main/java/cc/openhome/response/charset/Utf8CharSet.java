package cc.openhome.response.charset;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@WebServlet("/utf8CharSet")
public class Utf8CharSet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.printf("ContentType = %s%n", resp.getContentType());
        System.out.printf("CharacterEncoding = %s%n", resp.getCharacterEncoding());

        // This method may be called repeatedly to change locale and character encoding.
        // The method has no effect if called after the response has been committed.
        // It does not set the response's character encoding if it is called after
        // setContentType has been called with a charset specification,
        // after setCharacterEncoding has been called, after getWriter has been called,
        // or after the response has been committed
        resp.setLocale(Locale.TAIWAN);

        System.out.printf("ContentType = %s%n", resp.getContentType());
        System.out.printf("CharacterEncoding = %s%n", resp.getCharacterEncoding());

        resp.setContentType("text/plain; charset=UTF-8");

        System.out.printf("ContentType = %s%n", resp.getContentType());
        System.out.printf("CharacterEncoding = %s%n", resp.getCharacterEncoding());

        resp.setLocale(Locale.TAIWAN);

        System.out.printf("ContentType = %s%n", resp.getContentType());
        System.out.printf("CharacterEncoding = %s%n", resp.getCharacterEncoding());

        resp.getWriter().println("Hello world! 你好，世界！");
    }
}
