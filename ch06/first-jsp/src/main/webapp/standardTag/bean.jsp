<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="bookmark"
             class="com.example.firstjsp.model.Bookmark"
             type="com.example.firstjsp.model.BookmarkBase"
             scope="request">
    <%--也可以写在这 只有 getAttribute == null 时，会创建 bean 并赋值 （注意转移之后的 java 代码）--%>
    <jsp:setProperty name="bookmark" property="*"/>
</jsp:useBean>
<jsp:setProperty name="bookmark" property="title" value="一个标题"/>
<%--将请求参数 赋给 bookmark 各属性--%>
<jsp:setProperty name="bookmark" property="*"/>
<%--将指定请求参数 赋给 bookmark 指定属性--%>
<jsp:setProperty name="bookmark" property="category" param="title"/>
<!doctype html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>bean</title>
    </head>
    <body>
        <div>
            标题：<jsp:getProperty name="bookmark" property="title"/>
        </div>
        <div>
            分类：<jsp:getProperty name="bookmark" property="category"/>
        </div>
    </body>
</html>
