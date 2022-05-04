<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Title</title>
    </head>
    <body>
        <fmt:formatNumber value="12345.678"/><br>
        <fmt:formatNumber value="12345.678" type="currency"/><br>
        <fmt:formatNumber value="12345.678" type="currency" currencySymbol="新台币"/><br>
        <fmt:formatNumber value="12345.678" type="percent"/><br>
        <fmt:formatNumber value="12345.678" pattern="#,#00.0#"/>
    </body>
</html>
