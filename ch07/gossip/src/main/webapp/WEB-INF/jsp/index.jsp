<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>首页</title>
        <link ref="stylesheet" href="css/gossip.css" type="text/css">
    </head>
    <body>
        <div id="login">
            <div>
                <img src="image/caterpillar.jpg" alt="gossip">
            </div>
            <a href="register">还不是会员？</a>
            <%
                List<String> errors = (List<String>) request.getAttribute("errors");
                if (null != errors) {
            %>
            <ul style="color:rgb(255,0,0);">
                <%
                    for (String error : errors) {
                %>
                <li><%= error%></li>
                <%
                    }
                %>
            </ul>
            <%
                }
            %>
            <form action="login" method="post">
                <table>
                    <tr>
                        <td colspan="2">会员登录</td>
                    </tr>
                    <tr>
                        <td>名称：</td>
                        <td><input type="text" name="username" value="${param.username}"/></td>
                    </tr>
                    <tr>
                        <td>密码：</td>
                        <td><input type="password" name="password"/></td>
                    </tr>
                    <tr>
                        <td colspan="2"><input type="submit" value="登录"/></td>
                    </tr>
                    <tr>
                        <td colspan="2"><a href="forgot.html">忘记密码？</a></td>
                    </tr>
                </table>
            </form>
        </div>
        <div>
            <h1>Gossip ... XD</h1>
            <ul>
                <li>谈天说地不奇怪</li>
                <li>分享信息也可以</li>
                <li>随意谢谢表心情</li>
            </ul>
        </div>
    </body>
</html>