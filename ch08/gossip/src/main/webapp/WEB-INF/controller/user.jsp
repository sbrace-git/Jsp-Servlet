<%@ page import="cc.openhome.gossip.service.UserService" %>
<%@ page import="java.io.IOException" %>
<%@ page import="cc.openhome.gossip.model.Message" %>
<%@ page import="java.util.List" %>
<%!
    private UserService userService;

    private String USER_PATH;

    @Override
    public void jspInit() {
        userService = (UserService) getServletConfig().getServletContext().getAttribute("userService");
        USER_PATH = getServletConfig().getInitParameter("USER_PATH");
    }
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        System.out.printf("pathInfo = 【%s】", pathInfo);
        String username = pathInfo.substring(1);
        List<Message> messages = userService.messages(username);
        req.setAttribute("username", username);
        req.setAttribute("messages", messages);

        req.getRequestDispatcher(USER_PATH).forward(req, resp);
    }
%>
<%
    if ("GET".equals(request.getMethod())) {
        doGet(request,response);
    } else {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
%>