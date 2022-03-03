package cc.openhome.response.charset;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/defaultCharSet")
public class DefaultCharSet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /**
         * see
         * src/main/webapp/WEB-INF/web.xml
         * <request-character-encoding>
         * <response-character-encoding>
         */
        System.out.printf("contentType = %s%n", req.getContentType());
        System.out.printf("characterEncoding = %s%n", req.getCharacterEncoding());

        System.out.printf("ContentType = %s%n", resp.getContentType());
        System.out.printf("CharacterEncoding = %s%n", resp.getCharacterEncoding());
        resp.getWriter().println("Hello world! 你好，世界！");
    }
}
