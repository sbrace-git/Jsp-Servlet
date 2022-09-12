<%@ page import="java.io.IOException" %>
<%!
    private String LOGIN_PATH;

    @Override
    public void jspInit() {
        LOGIN_PATH = getServletConfig().getInitParameter("LOGIN_PATH");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().invalidate();
        resp.sendRedirect(LOGIN_PATH);
    }
%>
<%
    if ("GET".equals(request.getMethod())) {
        doGet(request, response);
    } else {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
%>