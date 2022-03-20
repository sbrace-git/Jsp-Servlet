package com.example.servletconfig;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/login",
        initParams = {
                @WebInitParam(name = "SUCCESS", value = "empty"),
                @WebInitParam(name = "ERROR", value = "empty"),
                @WebInitParam(name = "WebInitParam name",value = "WebInitParam value")
        })
public class Login extends HttpServlet {

    private static String SUCCESS;
    private static String ERROR;

    @Override
    public void init() throws ServletException {
        SUCCESS = getInitParameter("SUCCESS");
        ERROR = getInitParameter("ERROR");
        Collections.list(getInitParameterNames()).forEach(initParameterName -> {
            if (null == initParameterName) {
                System.out.println("initParameterName is null");
            }
            String initParameter = getInitParameter(initParameterName);
            if (null == initParameter) {
                System.out.println("initParameter is null");
            }
            System.out.printf("%s = 【%s】%n", initParameterName, initParameter);
        });
        System.out.printf("servlet init, ServletName = %s%n", getServletName());
//        SUCCESS = 【success.view】
//        null = 【null】
//        ERROR = 【error.view】
//        WebInitParam name = 【WebInitParam value】
//        empty = 【】
//        servlet init, ServletName = com.example.servletconfig.Login
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if ("caterpillar".equals(username) && "123456".equals(password)) {
            req.getRequestDispatcher(SUCCESS).forward(req, resp);
        } else {
            req.getRequestDispatcher(ERROR).forward(req, resp);
        }
    }
}
