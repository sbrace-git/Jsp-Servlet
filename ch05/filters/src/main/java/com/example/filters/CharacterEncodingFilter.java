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

/**
 * Servlet 4.0 之后，若是整个 Web 应用程序都要采用的默认编码，可以在 web.xml 中设定
 * <request-character-encoding></request-character-encoding>
 * <response-character-encoding></response-character-encoding>
 * 如果几个特有的 servlet 需要使用特定编码，可以用过滤器的方式实现
 */
@WebFilter(
        urlPatterns = "/*",
        initParams = {
                @WebInitParam(name = "encoding", value = "UTF-8")
        }
)
public class CharacterEncodingFilter extends HttpFilter {
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
