<%@ page import="java.time.LocalDateTime" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="UTF-8">
        <title>time</title>
    </head>
    <body>
        <%
            // 单行注释
            /*
             * 多行
             * 注释
             */
        %>
        <%=
            // 单行注释
            LocalDateTime.now()
            /*
             * 多行
             * 注释
             */
        %>
        <!-- 网页注释 -->
        <%--   jsp 注释    --%>
    </body>
</html>
