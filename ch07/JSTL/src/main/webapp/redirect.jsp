<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>redirect</title>
    </head>
    <body>
        <c:redirect url="add.jsp">
            <c:param name="a" value="1"/>
            <c:param name="b" value="2"/>
        </c:redirect>
    </body>
</html>
