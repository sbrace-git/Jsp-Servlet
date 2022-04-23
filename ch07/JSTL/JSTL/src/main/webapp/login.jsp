<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>login</title>
    </head>
    <body>
        <c:if test="${param.username == 'momor' && param.password == '1234'}">
            <h1>${param.username} 登录成功</h1>
        </c:if>
    </body>
</html>
