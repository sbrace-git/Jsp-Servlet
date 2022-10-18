<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="UTF-8">
        <title>verify</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${requestScope.account.present}">
                <h1>账号启动成功</h1>
            </c:when>
            <c:otherwise>
                <h1>账号启动失败</h1>
            </c:otherwise>
        </c:choose>
        <a href="/gossip">回首页</a>
    </body>
</html>
