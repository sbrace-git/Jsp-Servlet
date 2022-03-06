package cc.openhome.gossip.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

@WebServlet("/register")
public class Register extends HttpServlet {
    private final Pattern emailRegex = Pattern.compile(
            "^[_a-z0-9-]+([.][_a-z0-9-]+)*@[a-z0-9-]+([.][a-z0-9-]+)*$");

    private final Pattern passwdRegex = Pattern.compile("^\\w{8,16}$");

    private final Pattern usernameRegex = Pattern.compile("^\\w{1,16}$");

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String username1 = req.getParameter("username");
        String username = req.getParameter("username");
        String password2 = req.getParameter("password2");


    }

    private boolean validateEmail(String email) {
        return email != null && emailRegex.matcher(email).find();
    }
}
