<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set target="${pageContext.request}" property="characterEncoding" value="UTF-8"/>
<jsp:useBean id="guestbook" class="com.example.jdbcmetadata.GuestBookBeanRowSet" scope="application"/>
<c:if test="${param.msg != null}">
    <jsp:useBean id="newMessage" class="com.example.jdbcmetadata.Message"/>
    <jsp:setProperty name="newMessage" property="*"/>
    <% guestbook.addMessage(newMessage);%>
</c:if>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>guest book</title>
    </head>
    <body>
        <table style="text-align: left; width: 100%;" border="0" cellpadding="2" cellspacing="2">
            <tbody>
            <c:forEach var="message" items="${guestbook.messageList(param.searchName)}">
                <tr>
                    <td>${message.name}</td>
                    <td>${message.email}</td>
                    <td>${message.msg}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </body>
</html>
