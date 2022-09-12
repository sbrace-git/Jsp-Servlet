<%@ page import="cc.openhome.gossip.service.UserService" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%!
    private String MEMBER_PATH;

    private UserService userService;

    @Override
    public void jspInit() {
        MEMBER_PATH = getServletConfig().getInitParameter("MEMBER_PATH");
        userService = (UserService) getServletConfig().getServletContext().getAttribute("userService");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = (String) req.getSession().getAttribute("login");
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String blabla = req.getParameter("blabla");
        if (null == blabla || blabla.length() > 140 || blabla.length() == 0) {
            req.getRequestDispatcher(MEMBER_PATH).forward(req, resp);
            return;
        }
        userService.addMessage(username, blabla);
        resp.sendRedirect(MEMBER_PATH);
    }
%>
<%
    if ("POST".equals(request.getMethod())) {
        doPost(request, response);
    } else {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
%>