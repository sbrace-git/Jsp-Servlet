<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<!doctype html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>500</title>
    </head>
    <body>
        <h1>error page</h1>

        <div>
            <h2>发生异常：</h2>
            <%= exception %>
        </div>
        <div>
            <h2>异常堆栈：</h2>
            <%
                exception.printStackTrace(new PrintWriter(out));
                exception.printStackTrace();
            %>
        </div>
    </body>
</html>
