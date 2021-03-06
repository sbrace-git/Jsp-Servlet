package cc.openhome.gossip.filter;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@WebFilter(urlPatterns = "/new_message")
public class HtmlSanitizerFilter extends HttpFilter {
    private PolicyFactory policyFactory;

    @Override
    public void init() {
        policyFactory = new HtmlPolicyBuilder()
                .allowElements("a", "b", "i", "del", "pre", "code", "big", "small")
                .allowUrlProtocols("http", "https")
                .allowAttributes("href").onElements("a")
                .requireRelNofollowOnLinks()
                .toFactory();
    }

    private class SanitizerWrapper extends HttpServletRequestWrapper {

        /**
         * Constructs a request object wrapping the given request.
         *
         * @param request the {@link HttpServletRequest} to be wrapped.
         * @throws IllegalArgumentException if the request is null
         */
        public SanitizerWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getParameter(String name) {
            return Optional.ofNullable(super.getParameter(name))
                    .map(policyFactory::sanitize)
                    .orElse(null);
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            HashMap<String, String[]> parameterMap = new HashMap<>(super.getParameterMap());
            parameterMap.replaceAll((k,v) -> sanitizeValues(v));
            return parameterMap;
        }

        @Override
        public String[] getParameterValues(String name) {
            return Optional.ofNullable(super.getParameterValues(name))
                    .map(this::sanitizeValues)
                    .orElse(null);
        }

        private String[] sanitizeValues(String[] values) {
            return Arrays.stream(values).map(policyFactory::sanitize).toArray(String[]::new);
        }

    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(new SanitizerWrapper(req), res);
    }
}
