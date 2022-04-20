<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="/WEB-INF/error/error.jsp" %>
<!doctype html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>add</title>
    </head>
    <body>
        <div>
            <%
                int a = Integer.parseInt(request.getParameter("a"));
                int b = Integer.parseInt(request.getParameter("b"));
            %>
        </div>
        <div>
            <%= a%> + <%= b%> = <%= a + b %>
        </div>
    </body>
</html>
