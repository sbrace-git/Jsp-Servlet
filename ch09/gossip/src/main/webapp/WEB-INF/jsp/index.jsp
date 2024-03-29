<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Gossip 微博</title>
        <link href="css/gossip.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <div id="login">
            <div>
                <img src="image/caterpillar.jpg" alt="gossip">
            </div>
            <a href="register">还不是会员？</a>
            <c:if test="${not empty requestScope.errors}">
                <ul style="color:rgb(255,0,0);">
                    <c:forEach var="error" items="${requestScope.errors}">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </c:if>
            <form action="login" method="post">
                <table>
                    <tr>
                        <td colspan="2">会员登录</td>
                    </tr>
                    <tr>
                        <td>名称：</td>
                        <td><input type="text" name="username" value="${param.username}"/></td>
                    </tr>
                    <tr>
                        <td>密码：</td>
                        <td><input type="password" name="password"/></td>
                    </tr>
                    <tr>
                        <td colspan="2"><input type="submit" value="登录"/></td>
                    </tr>
                    <tr>
                        <td colspan="2"><a href="forgot.html">忘记密码？</a></td>
                    </tr>
                </table>
            </form>
        </div>
        <div>
            <h1>Gossip ... XD</h1>
            <ul>
                <li>谈天说地不奇怪</li>
                <li>分享信息也可以</li>
                <li>随意谢谢表心情</li>
            </ul>
        </div>
        <table style="background-color: #ffffff">
            <thead>
            <tr>
                <th>
                    <hr>
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="message" items="${requestScope.newMessageList}">
                <tr>
                    <td style="vertical-align: top;">
                            ${message.username}<br>
                            ${message.blabla}<br>
                            ${message.localDateTime}
                        <hr>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </body>
</html>