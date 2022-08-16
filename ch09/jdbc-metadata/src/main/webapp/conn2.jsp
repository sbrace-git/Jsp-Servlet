<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="db" class="com.example.jdbcmetadata.DatabaseBean"/>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>test datasource connection</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${db.connectedOk}">connection ok</c:when>
            <c:otherwise>connection fail</c:otherwise>
        </c:choose>
    </body>
</html>
