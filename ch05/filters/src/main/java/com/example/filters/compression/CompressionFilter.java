package com.example.filters.compression;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class CompressionFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String encodings = req.getHeader("Accept-Encoding");
        if (null != encodings && encodings.contains("gzip")) {
            System.out.println("CompressionFilter # doFilter gzip");
            CompressionWrapper compressionWrapper = new CompressionWrapper(res);
            compressionWrapper.setHeader("Content-Encoding", "gzip");
            super.doFilter(req, compressionWrapper, chain);
            compressionWrapper.finish();
        } else {
            super.doFilter(req, res, chain);
        }
    }
}
