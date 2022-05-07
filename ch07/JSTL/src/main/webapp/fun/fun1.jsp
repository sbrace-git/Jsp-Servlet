<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
    <head>
        <meta charset="UTF-8">
        <title>fun1</title>
    </head>
    <body>
        参数：${param.text} <br>
        长度: ${fn:length(param.text)}
    </body>
</html>
