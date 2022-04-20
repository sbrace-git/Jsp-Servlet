<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="bookmark"
             class="com.example.firstjsp.model.Bookmark"
             scope="request"/>
<jsp:setProperty name="bookmark" property="title" value="一个标题"/>
<jsp:setProperty name="bookmark" property="*"/>
<!doctype html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>bean</title>
    </head>
    <body>
        <div>
            标题：<jsp:getProperty name="bookmark" property="title"/>
        </div>
        <div>
            分类：<jsp:getProperty name="bookmark" property="category"/>
        </div>
    </body>
</html>
