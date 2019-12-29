<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>sqlcmd</title>
</head>
<body>
        <table border="1">
            <c:forEach var="userAction" items="${userName}">
                <tr>
                    <td>
                            ${userAction.name}
                    </td>
                    <td>
                            ${userAction.dbName}
                    </td>
                    <td>
                            ${userAction.action}
                    </td>
                </tr>
            </c:forEach>
        </table>

    <footer>
    </footer>
</body>
</html>
