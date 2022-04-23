<%@ page import="cc.openhome.gossip.model.Message" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>user</title>
        <link rel="stylesheet" href="../css/member.css" type="text/css">
    </head>
    <body>
        <div class='leftPanel'>
            <img src='../image/caterpillar.jpg' alt='Gossip 微博'/> <br/>
            <br>${requestScope.username}的微博
        </div>
        <table border="0" cellpadding="2" cellspacing="2">
            <thead>
            <tr>
                <th>
                    <hr>
                </th>
            </tr>
            </thead>
            <tbody>
            <%
                List<Message> messages = (List<Message>) request.getAttribute("messages");
                for (Message message : messages) {
            %>
            <tr>
                <td style="vertical-align: top;">
                    <%= message.getUsername()%><br>
                    <%= message.getBlabla()%><br>
                    <%= message.getLocalDateTime()%>
                    <hr>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </body>
</html>
