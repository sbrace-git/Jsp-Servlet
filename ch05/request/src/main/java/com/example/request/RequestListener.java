package com.example.request;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@WebListener
public class RequestListener implements ServletRequestListener {
    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest servletRequest = (HttpServletRequest) sre.getServletRequest();
        String servletPath = servletRequest.getServletPath();
        System.out.printf("RequestListener # requestInitialized servletPath = %s%n", servletPath);
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("RequestListener # requestDestroyed");
    }
}
