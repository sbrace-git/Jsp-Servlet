package com.example.filters;

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;

/**
 * 多个 filter 则先匹配 urlPatterns, 后匹配 servletNames
 * urlPatterns 和 servletNames 只要有一个匹配上就可以绑定该过滤器
 *
 */
@WebFilter(
        filterName = "timeFilter",
        urlPatterns = "/*",
        initParams = {
                @WebInitParam(name = "name", value = "value")
        },
        dispatcherTypes = {
                DispatcherType.REQUEST,
                DispatcherType.FORWARD,
                DispatcherType.ERROR,
                DispatcherType.ASYNC,
                DispatcherType.INCLUDE
        },
        // 不可用通配符 *
        // servletNames = "*。do"
        servletNames = "helloServlet"
)

public class TimeFilter extends HttpFilter {
    @Override
    public void init() {
        FilterConfig filterConfig = getFilterConfig();
        Collections.list(filterConfig.getInitParameterNames())
                .forEach(initParameterName -> System.out.printf("TimeFilter # init 【%s】 = 【%s】%n",
                        initParameterName, filterConfig.getInitParameter(initParameterName)));
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String filterName = getFilterName();
        System.out.printf("%s start %s%n", filterName, LocalDateTime.now());
        super.doFilter(req, res, chain);
        System.out.printf("%s end %s%n", filterName, LocalDateTime.now());
    }
}
