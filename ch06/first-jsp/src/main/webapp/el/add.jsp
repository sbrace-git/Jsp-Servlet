<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ page isELIgnored="false" %>--%>
<%--<%@ page errorPage="/WEB-INF/error/500.jsp" %>--%>
<!doctype html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>add</title>
    </head>
    <body>
<%--        <%--%>
<%--            int a = Integer.parseInt(request.getParameter("a"));--%>
<%--            int b = Integer.parseInt(request.getParameter("b"));--%>
<%--        %>--%>
<%--        <div>--%>
<%--            <%= a%> + <%= b%> = <%= a + b %>--%>
<%--        </div>--%>
        <div>
            ${param.a} + ${param.b} = ${param.a + param.b}
        </div>
        <div>
            <div>
                方法：${pageContext.request.method}
            </div>
            <div>
                参数：${pageContext.request.queryString}
            </div>
            <div>
                IP：${pageContext.request.remoteAddr}
            </div>
        </div>
    </body>
</html>
