package com.example.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@WebFilter("/*")
public class RequestHtmlEncodingFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        printParameter(req);
        HtmlEncodingWrapper htmlEncodingWrapper = new HtmlEncodingWrapper(req);
        printParameter(htmlEncodingWrapper);
        super.doFilter(htmlEncodingWrapper, res, chain);
    }

    private void printParameter(HttpServletRequest req) {
        Collections.list(req.getParameterNames()).forEach(parameterName ->
                System.out.printf("RequestHtmlEncodingFilter【%s】 = 【%s】%n", parameterName, req.getParameter(parameterName)));
    }
}
