<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" scope="page" class="java.util.Date"/>
<!DOCTYPE html>
<fmt:bundle basename="messages3">
    <html>
        <head>
            <meta charset="UTF-8">
            <title><fmt:message key="cc.openhome.title"/></title>
        </head>
        <body>
            <h1>
                <fmt:message key="cc.openhome.forUser">
                    <fmt:param value="${param.username}"/>
                    <fmt:param value="${now}"/>
                    <fmt:param value="${now}"/>
                </fmt:message>
            </h1>
        </body>
    </html>
</fmt:bundle>
