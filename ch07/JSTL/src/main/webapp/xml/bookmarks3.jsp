<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Title</title>
    </head>
    <body>
        <c:import var="xml" url="bookmarks.xml" charEncoding="UTF-8"/>
        <c:import var="xslt" url="${param.xslt}" charEncoding="UTF-8"/>
        <x:transform doc="${xml}" xslt="${xslt}">
            <x:param name="headline" value="在线书签"/>
        </x:transform>
    </body>
</html>
<%--http://localhost:8080/JSTL/xml/bookmarks3.jsp?xslt=bookmarksTable.xsl--%>
<%--http://localhost:8080/JSTL/xml/bookmarks3.jsp?xslt=bookmarksBulletin.xsl--%>

