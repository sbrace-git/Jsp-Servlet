<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<c:set target="${pageContext.request}" property="characterEncoding" value="UTF-8"/>
<html>
    <head>
        <meta charset="UTF-8">
        <title>question</title>
    </head>
    <body>
        <form action="question.jsp" method="post">
            <c:choose>
                <c:when test="${param.page == null or param.page == 'page1'}">
                    <div>
                        <span>问题一：</span><input type="text" name="p1q1">
                    </div>
                    <div>
                        <span>问题二：</span><input type="text" name="p1q2">
                    </div>
                    <div>
                        <input type="submit" name="page" value="page2">
                    </div>
                </c:when>
                <c:when test="${param.page == 'page2'}">
                    <c:set var="p1q1" value="${param.p1q1}" scope="session"/>
                    <c:set var="p1q2" value="${param.p1q2}" scope="session"/>
                    <div>
                        <span>问题三：</span><input type="text" name="p2q3">
                    </div>
                    <div>
                        <input type="submit" name="page" value="finish">
                    </div>
                </c:when>
                <c:when test="${param.page == 'finish'}">
                    <div>
                            ${sessionScope.p1q1}
                    </div>
                    <div>
                            ${sessionScope.p1q2}
                    </div>
                    <div>
                            ${param.p2q3}
                    </div>
                </c:when>
            </c:choose>
        </form>
    </body>
</html>
