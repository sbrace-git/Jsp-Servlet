<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="javax.servlet.jsp.jstl.core.Config" %>
<%@ page import="javax.servlet.jsp.jstl.fmt.LocalizationContext" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    Locale locale = new Locale("ja", "JP");
    ResourceBundle res = ResourceBundle.getBundle("hello", locale);
    Config.set(pageContext, Config.FMT_LOCALIZATION_CONTEXT,
            new LocalizationContext(res), PageContext.PAGE_SCOPE);
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Title</title>
    </head>
    <body>
        <fmt:message key="cc.openhome.hello"/>
    </body>
</html>
