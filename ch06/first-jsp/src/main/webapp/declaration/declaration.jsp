<%@include file="/WEB-INF/jspf/header.jspf" %>
<%!
    private String username = "caterpillar";
    private String password = "123456";

    public boolean checkUser(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void jspInit() {
        System.out.println("jspInit");
    }

    public void jspDestroy() {
        System.out.println("jspDestroy");
    }
%>
<%
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    boolean checkUser = checkUser(username, password);
    if (checkUser) {
%>
    <h1><%= username %> 登录成功</h1>
<%
    } else {
%>
    <h1>登录失败</h1>
<%
    }
%>
<%
    out.println("&lt;% 与 %&gt; 被用来作为 JSP 中 Java 语法的部分");
%>
<%@include file="/WEB-INF/jspf/footer.jspf" %>