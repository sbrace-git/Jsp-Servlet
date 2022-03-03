package cc.openhome.response.charset;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@WebServlet("/locale")
public class LocaleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.printf("Accept-Language = %s%n",req.getHeader("Accept-Language"));
        System.out.printf("locale = %s%n", req.getLocale());

        System.out.println();
        System.out.printf("Content-Language = %s%n",resp.getHeader("Content-Language"));
        System.out.printf("CharacterEncoding = %s%n",resp.getCharacterEncoding());
        System.out.printf("ContentType = %s%n", resp.getContentType());
        // locale-encoding-mapping from src/main/webapp/WEB-INF/web.xml
        resp.setLocale(Locale.SIMPLIFIED_CHINESE);
        System.out.println();
        System.out.printf("Content-Language = %s%n",resp.getHeader("Content-Language"));
        System.out.printf("CharacterEncoding = %s%n",resp.getCharacterEncoding());
        System.out.printf("ContentType = %s%n", resp.getContentType());

        System.out.println();
        resp.getHeaderNames().forEach(headerName -> System.out.printf("headerName = %s%n", headerName));
        resp.getWriter().println("Hello world! 你好，世界！");
    }
}
