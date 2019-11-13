<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>sqlcmd</title>
</head>
<body>
    <form action = "connect" method = "POST">
        <table>
            <tr>
                <td>database name:</td>
                <td><input type = "text" name = "database" /><br></td>
            </tr>
            <tr>
                <td>user name:</td>
                <td><input type = "text" name = "userName" /><br></td>
            </tr>
            <tr>
                <td>password:</td>
                <td><input type = "password" name = "password" /><br></td>
            </tr>
            <tr>
                <td></td>
                <td><input type = "submit" value = "submit" /></td>
            </tr>
        </table>
    </form>
</body>
</html>
