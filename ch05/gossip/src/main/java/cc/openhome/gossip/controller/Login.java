package cc.openhome.gossip.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/login")
public class Login extends HttpServlet {
    private final static String USERS = "D:\\common\\temp\\users";

    private final static String SUCCESS_PATH = "member";
    private final static String ERROR_PATH = "index.html";


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.printf("username = %s%n", username);
        System.out.printf("password = %s%n", password);

        String page;
        if (login(username, password)) {
            if (null != req.getSession(false)) {
                req.changeSessionId();
            }
            req.getSession().setAttribute("login", username);
            page = SUCCESS_PATH;
        } else {
            page = ERROR_PATH;
        }
        resp.sendRedirect(page);
    }

    private boolean login(String username, String password) {
        if (username != null && username.trim().length() != 0 &&
                password != null) {
            Path userhome = Paths.get(USERS, username);
            System.out.printf("userhome = %s%n", userhome);
            return Files.exists(userhome) && isCorrectPassword(password, userhome);
        }
        return false;
    }


    private boolean isCorrectPassword(String password, Path userhome) {
        Path profile = userhome.resolve("profile");
        try (BufferedReader reader = Files.newBufferedReader(profile)) {
            String[] data = reader.readLine().split("\t");
            int encrypt = Integer.parseInt(data[1]);
            int salt = Integer.parseInt(data[2]);
            return password.hashCode() + salt == encrypt;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
