<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>forward</title>
    </head>
    <body>
        <jsp:forward page="/add.jsp">
            <jsp:param name="a" value="2"/>
            <jsp:param name="b" value="3"/>
        </jsp:forward>
    </body>
</html>
