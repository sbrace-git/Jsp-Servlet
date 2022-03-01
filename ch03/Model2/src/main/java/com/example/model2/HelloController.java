package com.example.model2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/hello")
public class HelloController extends HttpServlet {

    private final HelloModel model = new HelloModel();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("user");
        String message = model.doHello(name);
        req.setAttribute("message", message);
        req.getRequestDispatcher("hello.view")
                .forward(req, resp);
    }
}
