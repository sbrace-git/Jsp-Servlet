<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page import="javax.servlet.jsp.jstl.fmt.LocalizationContext" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<%
    ResourceBundle taiwan = ResourceBundle.getBundle("hello", Locale.TAIWAN);
    ResourceBundle china = ResourceBundle.getBundle("hello", Locale.CHINA);
    ResourceBundle japan = ResourceBundle.getBundle("hello", Locale.JAPAN);
    ResourceBundle us = ResourceBundle.getBundle("hello", Locale.US);

    pageContext.setAttribute("taiwan", new LocalizationContext(taiwan));
    pageContext.setAttribute("china", new LocalizationContext(china));
    pageContext.setAttribute("japan", new LocalizationContext(japan));
    pageContext.setAttribute("us", new LocalizationContext(us));
%>
<html>
    <head>
        <meta charset="UTF-8">
    </head>
    <body>
        <fmt:message bundle="${taiwan}" key="cc.openhome.hello"/><br>
        <fmt:message bundle="${china}" key="cc.openhome.hello"/><br>
        <fmt:message bundle="${japan}" key="cc.openhome.hello"/><br>
        <fmt:message bundle="${us}" key="cc.openhome.hello"/><br>
    </body>
</html>
