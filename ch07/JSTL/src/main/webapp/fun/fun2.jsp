<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <meta charset="UTF-8">
        <title>fun2</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${fn:startsWith(param.text,'caterpillar')}">
                ${fn:replace(param.text, 'caterpillar', '良葛格')}
            </c:when>
            <c:otherwise>
                ${param.text}
            </c:otherwise>
        </c:choose>
    </body>
</html>
