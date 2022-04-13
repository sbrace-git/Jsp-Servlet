package cc.openhome.gossip.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@WebServlet("/member")
public class Member extends HttpServlet {

    private static final String USER = "D:\\common\\temp\\users";
    private static final String MEMBER_PATH = "member.view";
    private static final String LOGIN_PATH = "index.html";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = getLoginUsername(req);
        if (null == username) {
            resp.sendRedirect(LOGIN_PATH);
            return;
        }
        Map<Long, String> messages = messages(username);
        req.setAttribute("messages", messages);
        req.getRequestDispatcher(MEMBER_PATH).forward(req, resp);
    }

    private Map<Long, String> messages(String username) {
        Path userHome = Paths.get(USER, username);
        TreeMap<Long, String> messages = new TreeMap<>(Comparator.reverseOrder());

        try (DirectoryStream<Path> txts = Files.newDirectoryStream(userHome, "*.txt")) {
            for (Path txt : txts) {
                String millis = txt.getFileName().toString().replace(".txt", "");
                String blabla = Files.readAllLines(txt).stream().collect(Collectors.joining(System.lineSeparator()));
                messages.put(Long.valueOf(millis), blabla);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return messages;
    }


    private String getLoginUsername(HttpServletRequest req) {
        Object login = req.getSession().getAttribute("login");
        if (null == login) {
            return null;
        }
        return (String) login;
    }
}
