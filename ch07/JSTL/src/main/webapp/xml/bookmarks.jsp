<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%--<c:import var="xml" url="bookmarks.xml" charEncoding="UTF-8"/>--%>
<%--<x:parse var="bookmarks" doc="${xml}" scope="page"/>--%>
<x:parse var="bookmarks">
    <c:import url="bookmarks.xml" charEncoding="UTF-8"/>
</x:parse>
<%--<x:parse var="bookmarks">--%>
<%--    <bookmarks>--%>
<%--        <bookmark id="1">--%>
<%--            <title encoding="UTF-8">瓦良格网站</title>--%>
<%--            <url>https://openhome.cc</url>--%>
<%--            <category>程式设计</category>--%>
<%--        </bookmark>--%>
<%--        <bookmark id="2">--%>
<%--            <title encoding="UTF-8">JWorld@TW</title>--%>
<%--            <url>https://www.javaworld.com.tw</url>--%>
<%--            <category>技术论坛</category>--%>
<%--        </bookmark>--%>
<%--    </bookmarks>--%>
<%--</x:parse>--%>
<html>
    <head>
        <meta charset="UTF-8">
        <title>bookmarks</title>
    </head>
    <body>
        <x:out select="$bookmarks//bookmark[1]/title"/><br>
        <x:out select="$bookmarks//bookmark[2]/title"/><br>
        <x:out select="$pageScope:bookmarks//bookmark[2]/title"/><br>
        id = ${param.id} <br>
        <x:out select="$pageScope:bookmarks//bookmark[@id=$param:id]/title"/><br>
        <x:set var="title" select="$bookmarks//bookmark[@id=$param:id]/title"/>
        <x:out select="$title"/>
    </body>
</html>
