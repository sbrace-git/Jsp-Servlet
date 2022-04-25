<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<c:set var="count" value="${sessionScope.count + 1}" scope="session"/>
<html>
    <head>
        <meta charset="UTF-8">
        <title>count</title>
    </head>
    <body>
        <h1>JSP Count ${sessionScope.count}</h1>
        <a href="<c:url value='count.jsp'/>">递增</a>
<%--        <a href="count.jsp">递增</a>--%>
    </body>
</html>