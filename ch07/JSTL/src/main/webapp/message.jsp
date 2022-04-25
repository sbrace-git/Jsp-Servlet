<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="messageService" class="com.example.jstl.MessageService"/>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>留言板</title>
    </head>
    <body>
        <table style="text-align: left; width: 100%;" border="1">
            <tr>
                <td>名称</td>
                <td>信息</td>
            </tr>
            <c:forEach var="message" varStatus="a" items="${messageService.messages}">
                <tr>
                    <td>${message.name}</td>
                    <td>${message.text}</td>
                </tr>
            </c:forEach>
        </table>
        <%
            Map<String, String> map = new HashMap<>();
            map.put("a.b", "a.b");
            map.put("c d", "c d");
            map.put("a", "a.data");
            map.put("b", "b.data");
            map.put("c", "c.data");
            pageContext.setAttribute("map", map);
        %>
        <c:forEach items="${map}" var="entry">
            <div>
                key = ${entry.key}<br>
                value = ${entry.value}
            </div>
        </c:forEach>
        <hr>
        <c:forEach items="Java,C++,C,JavaScript" var="lang">
            ${lang}<br>
        </c:forEach>
        <hr>
        <c:forTokens items="Java:C++:C:JavaScript" var="lang" delims=":">
            ${lang}<br>
        </c:forTokens>
    </body>
</html>
