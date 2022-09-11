<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <meta charset="UTF-8">
        <title>xmlMessage</title>
    </head>
    <body>
        <c:import url="xmlMessage" var="xml" charEncoding="UTF-8"/>
        <c:set var="xmlns">
            xmlns="http://java.sun.com/xml/ns/jdbc"
        </c:set>
        <x:parse var="webRowSet" doc="${fn:replace(xml,xmlns,'')}"/>
        <h2>友站的留言</h2>
        <table border="1">
            <tr bgcolor="#00ff00">
                <th align="left">名称</th>
                <th align="left">邮件</th>
                <th align="left">留言</th>
            </tr>
            <x:forEach var="row" select="$webRowSet//currentRow">
                <tr>
                    <td><x:out select="$row/columnValue[2]"/></td>
                    <td><x:out select="$row/columnValue[3]"/></td>
                    <td><x:out select="$row/columnValue[4]"/></td>
                </tr>
            </x:forEach>
        </table>
    </body>
</html>
