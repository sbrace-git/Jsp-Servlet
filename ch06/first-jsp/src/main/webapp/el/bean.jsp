<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="utils" uri="https://openhome.cc/utils" %>
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
<%--<jsp:setProperty name="bookmark" property="category" param="title"/>--%>
<!doctype html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>bean</title>
    </head>
    <body>
        <div>
            标题：${bookmark.title}
        </div>
        <div>
            标题：${bookmark["title"]}
        </div>
        <br>
        <div>
            分类：${bookmark.category}
        </div>
        <div>
            分类：${bookmark["category"]}
        </div>
        <br>
        <%
            String[] names = {"a", "b", "c"};
            pageContext.setAttribute("names", names, PageContext.PAGE_SCOPE);
        %>
        <div>
            name1 = ${names[0]}
        </div>
        <div>
            name2 = ${names[1]}
        </div>
        <div>
            name3 = ${names[2]}
        </div>
        <br>
        <div>
            name1 = ${names["0"]}
        </div>
        <div>
            name2 = ${names["1"]}
        </div>
        <div>
            name3 = ${names["2"]}
        </div>
        <br>
        <%
            List<String> strings = Arrays.asList("a", "b", "c");
            pageContext.setAttribute("strings", strings, PageContext.PAGE_SCOPE);
        %>
        <div>
            string1 = ${strings[0]}
        </div>
        <div>
            string2 = ${strings[1]}
        </div>
        <div>
            string3 = ${strings[2]}
        </div>
        <br>
        <div>
            string4 = ${strings[param.index]}
        </div>
        <br>
        <%
            Map<String, String> map = new HashMap<>();
            map.put("a.b", "a.b");
            map.put("c d", "c d");
            map.put("a", "a.data");
            map.put("b", "b.data");
            map.put("c", "c.data");
            pageContext.setAttribute("map", map);
        %>
        <div>
            a.b = ${map["a.b"]}
        </div>
        <div>
            a.b = ${map.get("a.b")}
        </div>
        <div>
            c d = ${map["c d"]}
        </div>
        <div>
            c d = ${map.get("c d")}
        </div>
        <br>
        <div>
            datas : ${map[names[param.index]]}
        </div>
        <br>
        <div>
            names length = ${utils:length(strings)}
        </div>
        <div>
            length = ${utils:length(["1","2","3"])}
        </div>
        <br>
        <div>
            <div>
                ${x = 10}
            </div>
            <div>
                ${y = 20}
            </div>
            <div>
                ${x1 = 10; y1 = 20}
            </div>
            <div>
                ${x1 = 10; y1 = 20; 0}
            </div>
            <div>
                list = ${list = [1,2,3,3]}
            </div>
            <div>
                set = ${set = {1,2,3,3}}
            </div>
            <div>
                map = ${map = {"username":"name","password":"123456","username":"name"}}
            </div>
        </div>
        <br>
        <div>
            <div>
                firstName = ${firstName = "firstName"}
            </div>
            <div>
                lastName = ${lastName = "lastName"}
            </div>
            <div>
                <%-- 注意运算符号是 += --%>
                firstName + lastName = ${firstName += lastName}
            </div>
            <div>
                firstName toUpperCase ${firstName.toUpperCase()}
            </div>
        </div>
        <br>
        <div>
            pageContext.setAttribute ${pageContext.setAttribute("token","123")}
        </div>
        <div>
            Integer.parseInt ${Integer.parseInt("123")}
        </div>
        <div>
            Math.round ${Math.round(1.6)}
        </div>
        <div>
            Math.PI ${Math.PI}
        </div>
        <br>
        <div>
            <div>
                <%-- importClass importPackage importStatic --%>
                <%-- ${pageContext.ELContext.importHandler.importPackage("java.time")}--%>
                ${pageContext.ELContext.importHandler.importClass("java.time.LocalTime")}
            </div>
            <div>
                ${LocalTime.now()}
            </div>
        </div>
        <br>
        <div>
            new String ${String("new String")}
        </div>
        <br>
        <div>
            <div>lambda</div>
            <div>
                ${plus = (x,y) -> x + y}
            </div>
            <div>
                ${plus(1,2)}
            </div>
            <div>
                ${() -> plus(10,20) + plus(30,40)}
            </div>
        </div>
        <br>
        <div>
            <div>lambda stream</div>
            <div>
                ${names = ["Justin","monica","Irene"]}
            </div>
            <div>
                ${names.stream().filter(name -> name.length() == 5).toList()}
            </div>
        </div>
    </body>
</html>
