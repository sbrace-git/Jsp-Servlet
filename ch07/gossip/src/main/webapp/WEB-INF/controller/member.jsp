<%@ page import="cc.openhome.gossip.service.UserService" %>
<%@ page import="java.io.IOException" %>
<%@ page import="cc.openhome.gossip.model.Message" %>
<%@ page import="java.util.List" %>
<%!
    private String MEMBER_PATH;

    private UserService userService;

    @Override
    public void jspInit() {
        MEMBER_PATH = getServletConfig().getInitParameter("MEMBER_PATH");
        userService = (UserService) getServletConfig().getServletContext().getAttribute("userService");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = (String) req.getSession().getAttribute("login");
        List<Message> messages = userService.messages(username);
        req.setAttribute("messages", messages);
        req.getRequestDispatcher(MEMBER_PATH).forward(req, resp);
    }
%>
<%
    String method = request.getMethod();
    if ("GET".equals(method)) {
        doGet(request, response);
    } else if ("POST".equals(method)) {
        doPost(request, response);
    } else {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
%>