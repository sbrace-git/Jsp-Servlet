<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>out</title>
    </head>
    <body>
        <div>
            param.out = ${param.out}
        </div>
        <div>
            c:out = <c:out value="${param.out}"/>
        </div>
        <div>
            default c:out = <c:out value="${param.out}" default="blabla"/>
        </div>
        <div>
            escapeXml:false c:out = <c:out value="${param.out}" escapeXml="false"/>
        </div>
    </body>
</html>
