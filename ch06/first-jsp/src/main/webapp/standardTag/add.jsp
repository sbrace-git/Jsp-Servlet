<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int a = Integer.parseInt(request.getParameter("a"));
    int b = Integer.parseInt(request.getParameter("b"));
%>
<div>
    <%= a%> + <%= b%> = <%= a + b %>
</div>

