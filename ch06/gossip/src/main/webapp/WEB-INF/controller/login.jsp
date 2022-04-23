<%@ page import="cc.openhome.gossip.service.UserService" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.ArrayList" %>
<%!
    private String SUCCESS_PATH;

    private String LOGIN_PATH;

    private UserService userService;

    @Override
    public void jspInit() {
        ServletConfig servletConfig = getServletConfig();
        SUCCESS_PATH = servletConfig.getInitParameter("SUCCESS_PATH");
        LOGIN_PATH = servletConfig.getInitParameter("LOGIN_PATH");
        userService = (UserService) servletConfig.getServletContext().getAttribute("userService");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (userService.login(username, password)) {
            if (null != req.getSession(false)) {
                req.changeSessionId();
            }
            req.getSession().setAttribute("login", username);
            resp.sendRedirect(SUCCESS_PATH);
        } else {
            ArrayList<String> errors = new ArrayList<>();
            errors.add("用户名或密码错误");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher(LOGIN_PATH).forward(req, resp);
        }
    }
%>
<%
    String method = request.getMethod();
    if ("POST".equals(method)) {
        doPost(request,response);
    } else {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
%>