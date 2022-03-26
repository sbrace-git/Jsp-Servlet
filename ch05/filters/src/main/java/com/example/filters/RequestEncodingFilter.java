package com.example.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@WebFilter(urlPatterns = "/**")
public class RequestEncodingFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String filterName = getFilterName();
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        System.out.printf("%s start %s%n", filterName, LocalDateTime.now());
        super.doFilter(req, res, chain);
        System.out.printf("%s end %s%n", filterName, LocalDateTime.now());
    }
}
