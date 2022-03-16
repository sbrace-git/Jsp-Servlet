package cc.openhome.gossip.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

@WebServlet("/register")
public class Register extends HttpServlet {
    private final static Pattern emailRegex = Pattern.compile(
            "^[_a-z0-9-]+([.][_a-z0-9-]+)*@[a-z0-9-]+([.][a-z0-9-]+)*$");

    private final static Pattern passwdRegex = Pattern.compile("^\\w{8,16}$");

    private final static Pattern usernameRegex = Pattern.compile("^\\w{1,16}$");

    private final static String SUCCESS_PATH = "register_success.view";
    private final static String ERROR_PATH = "register_error.view";
    private final static String USERS = "D:\\common\\temp\\users";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String password2 = req.getParameter("password2");

        System.out.printf("email = %s%n", email);
        System.out.printf("username = %s%n", username);
        System.out.printf("password = %s%n", password);
        System.out.printf("password2 = %s%n", password2);

        List<String> errors = new ArrayList<>();
        if (!validateEmail(email)) {
            errors.add("未填写邮件或邮件格式不正确");
        }
        if (!validateUsername(username)) {
            errors.add("未填写用户名或格式不正确");
        }
        if (!validatePassword(password, password2)) {
            errors.add("请确认密码符合格式并再次确认密码");
        }

        String path;
        if (errors.isEmpty()) {
            path = SUCCESS_PATH;
            tryCreateUser(email, username, password);
        } else {
            path = ERROR_PATH;
            req.setAttribute("errors", errors);
        }
        System.out.println(path);
        errors.forEach(System.out::println);
        req.getRequestDispatcher(path).forward(req, resp);
    }

    private boolean validateEmail(String email) {
        return email != null && emailRegex.matcher(email).find();
    }

    private boolean validateUsername(String username) {
        return username != null && usernameRegex.matcher(username).find();
    }

    private boolean validatePassword(String password, String password2) {
        return password != null &&
                passwdRegex.matcher(password).find() &&
                password.equals(password2);
    }

    private void tryCreateUser(String email, String username, String password) throws IOException {
        Path userHome = Paths.get(USERS, username);
        if (Files.notExists(userHome)) {
            createUser(userHome, email, password);
        }
    }

    private void createUser(Path userHome, String email, String password) throws IOException {
        Files.createDirectories(userHome);
        int salt = ThreadLocalRandom.current().nextInt();
        String encrypt = String.valueOf(salt + password.hashCode());
        Path profile = userHome.resolve("profile");
        String format = String.format("%s\t%s\t%d", email, encrypt, salt);
        System.out.println(format);
        try (BufferedWriter writer = Files.newBufferedWriter(profile)) {
            writer.write(format);
        }
    }
}
