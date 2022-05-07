<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<x:parse var="bookmarks">
    <c:import url="bookmarks.xml" charEncoding="UTF-8"/>
</x:parse>
<html>
    <head>
        <meta charset="UTF-8">
        <title>bookmarks2</title>
    </head>
    <body>
        <x:if select="$bookmarks//bookmark[@id=$param:id]/title">
            <x:out select="$bookmarks//bookmark[@id=$param:id]/title"/>
        </x:if>
        <br>
        <x:choose>
            <x:when select="$bookmarks//bookmark[@id=$param:id]/title">
                <x:out select="$bookmarks//bookmark[@id=$param:id]/title"/>
            </x:when>
            <x:otherwise>
                指定的书签 id = ${param.id} 不存在
            </x:otherwise>
        </x:choose>
        <br>
        <table border="1">
            <tr bgcolor="#00ff00">
                <th align="left">名称</th>
                <th align="left">网址</th>
                <th align="left">分类</th>
            </tr>
            <x:forEach var="bookmark" select="$bookmarks//bookmark">
                <tr>
                    <td><x:out select="$bookmark/title"/></td>
                    <td><x:out select="$bookmark/url"/></td>
                    <td><x:out select="$bookmark/category"/></td>
                </tr>
            </x:forEach>
        </table>
    </body>
</html>