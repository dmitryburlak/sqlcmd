<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>sqlcmd</title>
</head>
<body>
    <form action = "drop" method = "POST">
            <table>
                <tr>
                    <td>drop table name:</td>
                    <td><input type = "text" name = "tableName" /><br></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type = "submit" value = "submit" /></td>
                </tr>
            </table>
    </form>
    <tr>
        <td>${message}</td>
        <td><a href="menu">menu</a></td>
    </tr>
</body>
</html>
