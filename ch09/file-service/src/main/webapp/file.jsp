<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="fileService" class="com.example.fileservice.service.FileService" scope="application"/>
<html>
    <head>
        <meta charset="UTF-8">
        <title>file</title>
    </head>
    <body>
        <form method="post" action="upload" enctype="multipart/form-data">
            <br>
            选取文件: <input type="file" name="file"><br><br>
            <input type="submit" value="上传">
        </form>
        <hr>
        <table style="text-align: left" border="1" cellpadding="2" cellspacing="2">
            <tbody>
            <tr>
                <td>文件名</td>
                <td>上传时间</td>
                <td>操作</td>
            </tr>
            <c:forEach var="file" items="${fileService.fileList}">
                <tr>
                    <td>${file.fileName}</td>
                    <td>${file.createTime}</td>
                    <td>
                        <a href="download?id=${file.id}">下载</a>
                        <a href="delete?id=${file.id}">删除</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </body>
</html>
