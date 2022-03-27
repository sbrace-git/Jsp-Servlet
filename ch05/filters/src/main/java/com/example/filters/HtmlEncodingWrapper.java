package com.example.filters;

import org.owasp.encoder.Encode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Optional;

public class HtmlEncodingWrapper extends HttpServletRequestWrapper {

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request the {@link HttpServletRequest} to be wrapped.
     * @throws IllegalArgumentException if the request is null
     */
    public HtmlEncodingWrapper(HttpServletRequest request) {
        super(request);
    }


    @Override
    public String getParameter(String name) {
        return Optional.ofNullable(super.getParameter(name))
                .map(Encode::forHtml)
                .orElse(null);
    }
}
