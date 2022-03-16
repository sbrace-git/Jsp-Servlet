package cc.openhome.gossip.view;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/member.view")
public class MemberView extends HttpServlet {

    private final static String LOGIN_PATH = "index.html";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = getLoginUsername(req);
        if (null == username) {
            resp.sendRedirect(LOGIN_PATH);
            return;
        }

    }

    public static void main(String[] args) {
        String  str = null;
        Object str1 = (Object) str;
        String str2 = (String) str1;
        System.out.println(str2);

    }

    private String getLoginUsername(HttpServletRequest req) {
        return (String) Optional.ofNullable(req.getSession(false))
                .map(session -> session.getAttribute("login")).orElse(null);
    }
}
