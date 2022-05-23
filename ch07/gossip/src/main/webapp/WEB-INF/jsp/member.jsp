<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Gossip 微博</title>
        <link rel='stylesheet' href='css/member.css' type='text/css'/>
    </head>
    <body>
        <div class='leftPanel'>
            <img src='image/caterpillar.jpg' alt='Gossip 微博'/> <br/>
            <a href='logout'>注销 ${sessionScope.login}
            </a>
        </div>
        <form method='post' action='new_message'>
            分享新鲜事...<br/>
            <c:if test="${not empty param.blabla}">
                讯息要在140字内<br>
            </c:if>
            <textarea cols='60' rows='4' name='blabla'>${param.blabla}</textarea>
            <br/>
            <button type='submit'>发送</button>
        </form>
        <c:choose>
            <c:when test="${empty requestScope.messages}">
                写点什么吧...
            </c:when>
            <c:otherwise>
                <table border='0' cellpadding='2' cellspacing='2'>
                    <thead>
                    <tr>
                        <th>
                            <hr>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="message" items="${requestScope.messages}">
                        <tr>
                            <td style='vertical-align: top;'>
                                    ${message.username}<br>
                                    ${message.blabla}<br>
                                    ${message.localDateTime}
                                <form method='post' action='del_message'>
                                    <input type="hidden" name="millis" value="${message.millis}">
                                    <button type="submit">删除</button>
                                </form>
                                <hr>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </body>
</html>
