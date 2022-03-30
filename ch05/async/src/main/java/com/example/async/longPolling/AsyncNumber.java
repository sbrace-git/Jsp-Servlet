package com.example.async.longPolling;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet(urlPatterns = "/asyncNumber", asyncSupported = true)
public class AsyncNumber extends HttpServlet {

    private List<AsyncContext> asyncList;

    @Override
    public void init() {
        asyncList = (List<AsyncContext>) getServletContext().getAttribute("asyncList");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        synchronized (asyncList) {
            asyncList.add(req.startAsync());
        }
    }
}
