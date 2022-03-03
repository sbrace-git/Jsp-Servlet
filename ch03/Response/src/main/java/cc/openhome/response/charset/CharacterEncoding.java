package cc.openhome.response.charset;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/CharacterEncoding")
public class CharacterEncoding extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        System.out.printf("CharacterEncoding = %s%n",resp.getCharacterEncoding());

        resp.getWriter().println("Hello world! 你好，世界！");
    }
}
