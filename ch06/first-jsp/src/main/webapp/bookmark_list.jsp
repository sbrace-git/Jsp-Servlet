<%@ page import="com.example.firstjsp.service.BookmarkService,com.example.firstjsp.model.Bookmark"
         contentType="text/html;charset=UTF-8"
         language="java" %>
<!doctype html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>观看在线书签</title>
    </head>
    <body>
        <table style="text-align: left; width: 100%;" border="0">
            <tbody>
                <tr>
                    <td style="background-color: rgb(51,255,255);">网页</td>
                    <td style="background-color: rgb(51,255,255);">分类</td>
                </tr>
                <%
                    BookmarkService bookmarkService = (BookmarkService) application.getAttribute("bookmarkService");
                    for (Bookmark bookmark : bookmarkService.getBookmarkList()) {
                %>
                <tr>
                    <td><a href="http://<%= bookmark.getUrl()%>"><%=bookmark.getTitle()%></a></td>
                    <td><%= bookmark.getTitle()%></td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </body>
</html>
