<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<sql:setDataSource dataSource="jdbc/demo"/>
<c:set target="${pageContext.request}" property="characterEncoding" var="UTF-8"/>
<c:if test="${param.msg != null}">
    <sql:update>
        insert into t_message (name,email,msg) values (?,?,?)
        <sql:param value="${param.name}"/>
        <sql:param value="${param.email}"/>
        <sql:param value="${param.msg}"/>
    </sql:update>
</c:if>
<html>
    <head>
        <meta charset="UTF-8">
        <title>访客留言板</title>
    </head>
    <body>
        <table style="text-align: left; width: 100%;" border="0" cellpadding="2" cellspacing="2">
            <sql:query sql="select name,email,msg from t_message" var="messages"/>
            <c:forEach var="message" items="${messages.rows}">
                <tr>
                    <td>${message.name}</td>
                    <td>${message.email}</td>
                    <td>${message.msg}</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
