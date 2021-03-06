package com.example.fragment;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/fragmentAnnotation", loadOnStartup = 0)
public class FragmentAnnotation extends HttpServlet {

    @Override
    public void init() {
        System.out.println("fragmentAnnotation init");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain; charset=UTF-8");
        resp.getWriter().print(getServletName());
    }
}
