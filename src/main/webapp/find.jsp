<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>sqlcmd</title>
</head>
<body>
    <table border="1">
        <c:forEach var="row" items="${table}">
            <tr>
                <c:forEach var="element" items="${row}">
                    <td>
                        ${element}
                    </td>
                </c:forEach>
            </tr>
        </c:forEach>
    </table>
    <footer>
        <a href="menu">menu</a><br>
    </footer>
</body>
</html>
