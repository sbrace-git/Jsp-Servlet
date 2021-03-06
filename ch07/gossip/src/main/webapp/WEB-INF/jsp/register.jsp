<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>register</title>
        <link rel="stylesheet" href="css/gossip.css" type="text/css">
    </head>
    <body>
        <h1>会员申请</h1>
        <c:if test="${not empty requestScope.errors}">
            <c:forEach var="error" items="${requestScope.errors}">
                <ul style="color:rgb(255,0,0);">
                    <li> ${error} </li>
                </ul>
            </c:forEach>
        </c:if>
        <form action="register" method="post">
            <table>
                <tr>
                    <td>邮件地址：</td>
                    <td><input type="text" name="email" size="25" maxlength="100" value="${param.email}"/></td>
                </tr>
                <tr>
                    <td>名称（最大 16 字节）：</td>
                    <td><input type="text" name="username" size="25" maxlength="16" value="${param.username}"/></td>
                </tr>
                <tr>
                    <td>密码（8 到 16 字节）</td>
                    <td><input type="password" name="password" size="25" maxlength="16"/></td>
                </tr>
                <tr>
                    <td>确认密码：</td>
                    <td><input type="password" name="password2" size="25" maxlength="16"/></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="注册"/></td>
                </tr>
            </table>
        </form>
        <a href='/gossip'>返回首面</a>
    </body>
</html>
