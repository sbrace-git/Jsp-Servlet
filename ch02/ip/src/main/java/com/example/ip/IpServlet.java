package com.example.ip;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.stream.Collectors;

@WebServlet("/ip")
public class IpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HashMap<String, Object> data = new HashMap<>();
        data.put("dateTime", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        data.put("remoteAddr", req.getRemoteAddr());
        data.put("parameters", req.getParameterMap());
        data.put("queryString", req.getQueryString());
        data.put("json", req.getReader().lines().collect(Collectors.joining()));
        resp.setContentType("text/json; charset=UTF-8");

        resp.getWriter().print(new ObjectMapper().writeValueAsString(data));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}
