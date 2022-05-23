<%@ page import="cc.openhome.gossip.service.UserService" %>
<%@ page import="java.io.IOException" %>
<%!
    private String MEMBER_PATH;

    private UserService userService;

    @Override
    public void jspInit() {
        MEMBER_PATH = getServletConfig().getInitParameter("MEMBER_PATH");
        userService = (UserService) getServletConfig().getServletContext().getAttribute("userService");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("login");
        String millis = req.getParameter("millis");
        if (null != millis) {
            userService.deleteMessage(username, millis);
        }
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