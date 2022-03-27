package com.example.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebFilter(
        urlPatterns = "/*",
        initParams = {
                @WebInitParam(name = "encoding", value = "UTF-8")
        }
)
public class RequestCharacterEncodingFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String encoding = getInitParameter("encoding");
        req.setCharacterEncoding(encoding);
        res.setCharacterEncoding(encoding);

        String filterName = getFilterName();
        System.out.printf("%s start %s%n", filterName, LocalDateTime.now());
        super.doFilter(req, res, chain);
        System.out.printf("%s end %s%n", filterName, LocalDateTime.now());
    }
}
