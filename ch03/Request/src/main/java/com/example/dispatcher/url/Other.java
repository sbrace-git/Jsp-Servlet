package com.example.dispatcher.url;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/other-url")
public class Other extends HttpServlet {

    private static final Logger logger = Logger.getLogger(Other.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        logger.log(Level.INFO, "contextPath = {0}", req.getContextPath());
        logger.log(Level.INFO, "requestURL = {0}", req.getRequestURL().toString());
        logger.log(Level.INFO, "requestURI = {0}", req.getRequestURI());
        logger.log(Level.INFO, "servletPath = {0}", req.getServletPath());
        logger.log(Level.INFO, "pathInfo = {0}", req.getPathInfo());
        logger.log(Level.INFO, "queryString = {0}", req.getQueryString());
        logger.log(Level.INFO, "mapping = {0}\n", req.getHttpServletMapping().getMappingMatch().name());

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
        logger.log(Level.INFO, "forward_mapping = {0}",
                Optional.ofNullable(req.getAttribute(RequestDispatcher.FORWARD_MAPPING))
                        .map(obj -> (HttpServletMapping) obj)
                        .map(HttpServletMapping::getMappingMatch)
                        .map(MappingMatch::name)
                        .orElse("null"));

        resp.getWriter().println("Other do one...");
    }
}
