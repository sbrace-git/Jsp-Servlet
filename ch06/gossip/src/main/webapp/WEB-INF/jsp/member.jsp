<%@ page import="java.util.Optional" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.Instant" %>
<%@ page import="java.time.ZoneId" %>
<%!
    private String getLoginUsername(HttpServletRequest req) {
        return (String) Optional.ofNullable(req.getSession(false))
                .map(session -> session.getAttribute("login")).orElse(null);
    }
%>
<!doctype html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Gossip 微博</title>
        <link rel='stylesheet' href='css/member.css' type='text/css'/>
    </head>
    <body>
        <%
            String username = getLoginUsername(request);
        %>
        <div class='leftPanel'>
            <img src='image/caterpillar.jpg' alt='Gossip 微博'/> <br/>
            <a href='logout'>注销 <%= username %>
            </a>
        </div>
        <form method='post' action='new_message'>
            分享新鲜事...<br/>
            <%
                String preBlabla = request.getParameter("blabla");
                if (null == preBlabla) {
                    preBlabla = "";
                } else {
            %>
            讯息要在140字内<br>
            <%
                }
            %>
            <textarea cols='60' rows='4' name='blabla'><%= preBlabla %></textarea>
            <br/>
            <button type='submit'>发送</button>
        </form>
        <%
            Map<Long, String> messages = (Map<Long, String>) request.getAttribute("messages");
            if (null == messages || messages.size() == 0) {
        %>
        写点什么吧...
        <%
        } else {
        %>

        <table border='0' cellpadding='2' cellspacing='2'>
            <thead>
            <tr>
                <th>
                    <hr>
                </th>
            </tr>
            </thead>
            <tbody>
            <%
                for (Map.Entry<Long, String> message : messages.entrySet()) {
                    Long millis = message.getKey();
                    String blabla = message.getValue();
                    LocalDateTime dateTime = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault()).toLocalDateTime();
            %>
            <tr>
                <td style='vertical-align: top;'>
                    <%= username %><br>
                    <%= blabla %><br>
                    <%= dateTime %>
                    <form method='post' action='del_message'>
                        <input type="hidden" name="millis" value="<%= millis %>">
                        <button type="submit">删除</button>
                    </form>
                    <hr>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
        <%
            }
        %>
    </body>
</html>
