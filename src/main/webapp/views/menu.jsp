<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>sqlcmd</title>
</head>
<body>
    <c:forEach var="item" items="${items}">
        <a href="${item}">${item}</a><br>
    </c:forEach>
</body>
</html>
