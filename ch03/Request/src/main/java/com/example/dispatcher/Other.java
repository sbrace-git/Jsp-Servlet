package com.example.dispatcher;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/other")
public class Other extends HttpServlet {

    private static final Logger logger = Logger.getLogger(Other.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        logger.log(Level.INFO, "\ncharacterEncoding = {0}\n", req.getCharacterEncoding());

        logger.info("\n headers:");
        Collections.list(req.getHeaderNames())
                .forEach(headerName ->
                        logger.log(Level.INFO,"{0} = {1}", new Object[]{headerName,req.getHeader(headerName)}));

        logger.info("\n dispatcher:");
        logger.log(Level.INFO, "include_context_path = {0}", req.getAttribute(RequestDispatcher.INCLUDE_CONTEXT_PATH));
        logger.log(Level.INFO, "include_request_uri = {0}", req.getAttribute(RequestDispatcher.INCLUDE_REQUEST_URI));
        logger.log(Level.INFO, "include_servlet_path = {0}", req.getAttribute(RequestDispatcher.INCLUDE_SERVLET_PATH));
        logger.log(Level.INFO, "include_path_info = {0}", req.getAttribute(RequestDispatcher.INCLUDE_PATH_INFO));
        logger.log(Level.INFO, "include_query_string = {0}", req.getAttribute(RequestDispatcher.INCLUDE_QUERY_STRING));
        logger.log(Level.INFO, "include_mapping = {0}\n",
                Optional.ofNullable(req.getAttribute(RequestDispatcher.INCLUDE_MAPPING))
                        .map(obj -> (HttpServletMapping) obj)
                        .map(HttpServletMapping::getMappingMatch)
                        .map(MappingMatch::name)
                        .orElse("null"));

        logger.log(Level.INFO, "forward_context_path = {0}", req.getAttribute(RequestDispatcher.FORWARD_CONTEXT_PATH));
        logger.log(Level.INFO, "forward_request_uri = {0}", req.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI));
        logger.log(Level.INFO, "forward_servlet_path = {0}", req.getAttribute(RequestDispatcher.FORWARD_SERVLET_PATH));
        logger.log(Level.INFO, "forward_path_info = {0}", req.getAttribute(RequestDispatcher.FORWARD_PATH_INFO));
        logger.log(Level.INFO, "forward_query_string = {0}", req.getAttribute(RequestDispatcher.FORWARD_QUERY_STRING));
        logger.log(Level.INFO, "forward_mapping = {0}\n",
                Optional.ofNullable(req.getAttribute(RequestDispatcher.FORWARD_MAPPING))
                        .map(obj -> (HttpServletMapping) obj)
                        .map(HttpServletMapping::getMappingMatch)
                        .map(MappingMatch::name)
                        .orElse("null"));

        logger.log(Level.INFO, "contextPath = {0}", req.getContextPath());
        logger.log(Level.INFO, "requestURL = {0}", req.getRequestURL().toString());
        logger.log(Level.INFO, "servletPath = {0}", req.getServletPath());
        logger.log(Level.INFO, "pathInfo = {0}", req.getPathInfo());
        logger.log(Level.INFO, "queryString = {0}", req.getQueryString());
        logger.log(Level.INFO, "mapping = {0}", req.getHttpServletMapping().getMappingMatch().name());

        logger.info("\n parameters:");
        req.getParameterMap().forEach((k, v) ->
                logger.log(Level.INFO, "{0} = {1}", new Object[]{k, Arrays.toString(v)}));

        logger.info("\n attributes:");
//        req.removeAttribute("books");
        Collections.list(req.getAttributeNames()).forEach(attributeName ->
                logger.log(Level.INFO, "{0} = {1}", new Object[]{attributeName, req.getAttribute(attributeName)}));

        req.setCharacterEncoding(StandardCharsets.ISO_8859_1.name());
        logger.log(Level.INFO, "\ncharacterEncoding = {0}\n", req.getCharacterEncoding());

        logger.log(Level.INFO, "\nresponse header SomeInclude = {0}\n", resp.getHeader("SomeInclude"));
        resp.setHeader("Other","Other");
        logger.log(Level.INFO, "\nresponse header Other = {0}\n", resp.getHeader("Other"));
        resp.setHeader("SomeInclude","Other");
        logger.log(Level.INFO, "\nresponse header SomeInclude = {0}\n", resp.getHeader("SomeInclude"));

        resp.getWriter().println("Other do one...");
    }
}
