<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="bookmark"
             class="com.example.firstjsp.model.Bookmark"
             scope="request"/>
<jsp:setProperty name="bookmark" property="title" value="一个标题"/>
<!doctype html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>bean</title>
    </head>
    <body>
        <h1>
            <jsp:getProperty name="bookmark" property="title"/>
        </h1>
    </body>
</html>
