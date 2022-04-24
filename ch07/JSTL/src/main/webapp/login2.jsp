<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" class="com.example.jstl.User"/>
<jsp:setProperty name="user" property="*"/>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>login</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${user.valid()}">
                你好，${user.username}
            </c:when>
            <c:otherwise>
                登录失败
            </c:otherwise>
        </c:choose>
    </body>
</html>
