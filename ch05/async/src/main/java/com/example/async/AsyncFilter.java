package com.example.async;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(
        urlPatterns = "/asyncServlet"
        , asyncSupported = true
)
public class AsyncFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("AsyncFilter start");
        super.doFilter(req, res, chain);
        // service 方法执行完，会打印【AsyncFilter end】。不会等待异步代码执行完成
        System.out.println("AsyncFilter end");
    }
}
