<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>include</title>
    </head>
    <body>
        <jsp:include page="add.jsp">
            <jsp:param name="a" value="1"/>
            <jsp:param name="b" value="2"/>
        </jsp:include>
    </body>
</html>
