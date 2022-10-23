<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>user</title>
        <link rel="stylesheet" href="../css/member.css" type="text/css">
    </head>
    <body>
        <div class='leftPanel'>
            <img src='../image/caterpillar.jpg' alt='Gossip 微博'/> <br/>
            <br>${requestScope.username}的微博
        </div>
        <table border="0" cellpadding="2" cellspacing="2">
            <thead>
            <tr>
                <th>
                    <hr>
                </th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${not empty requestScope.errors}">
                    <ul>
                        <c:forEach var="error" items="${requestScope.errors}">
                            <li>${error}</li>
                        </c:forEach>
                    </ul>
                </c:when>
                <c:otherwise>
                    <c:forEach var="message" items="${requestScope.messages}">
                        <tr>
                            <td style="vertical-align: top;">
                                    ${message.username}<br>
                                    ${message.blabla}<br>
                                    ${message.localDateTime}
                                <hr>
                            </td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </body>
</html>
