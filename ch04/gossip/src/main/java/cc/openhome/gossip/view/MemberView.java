package cc.openhome.gossip.view;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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

        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<meta charset='UTF-8'/>");
        writer.println("<title>Gossip 微博</title>");
        writer.println("<link rel='stylesheet' href='css/member.css' type='text/css' />");
        writer.println("</head>");

        writer.println("<body>");
        writer.println("<div class='leftPanel'>");
        writer.println("<img src='images/caterpillar.jpg' alt='Gossip 微博' /> <br/>");
        writer.printf("<a href='logout'>注销 %s</a>", username);
        writer.println("</div>");

        writer.println("<form method='post' action='new_message'>");
        writer.println("分享新鲜事...<br/>");

        String preBlabla = req.getParameter("blabla");
        if (null == preBlabla) {
            preBlabla = "";
        } else {
            writer.println("讯息要在140字内<br/>");
        }
        writer.printf("<textarea cols='60' rows='4' name='blabla'>%s</textarea>", preBlabla);
        writer.println("</form>");

        Map<Long, String> messages = (Map<Long, String>) req.getAttribute("messages");
        if (null == messages || messages.isEmpty()) {
            writer.println("写点什么吧...");
        } else {
            writer.println("<table border='0' cellpadding='2' cellspacing='2'>");
            writer.println("<thead>");
            writer.println("<tr><th><hr/></th></tr>");
            writer.println("</thead>");
            writer.println("<tbody>");

            for (Map.Entry<Long, String> message : messages.entrySet()) {
                Long millis = message.getKey();
                String blabla = message.getValue();

                LocalDateTime localDateTime = Instant.ofEpochMilli(millis)
                        .atZone(ZoneId.systemDefault()).toLocalDateTime();
                writer.println("<tr><td style='vertical-align: top;'>");
                writer.println("");
            }

            writer.println("</tbody>");
            writer.println("</table>");
        }

        writer.println("</body>");
        writer.println("</html>");


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
