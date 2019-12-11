<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>sqlcmd</title>
</head>
<body>
    <form:form action = "connect" method = "POST" modelAttribute="connection">
       <form:input type="hidden" path="fromPage"/>
        <table>
            <tr>
                <td>database name:</td>
                <td><form:input path = "database" /><br></td>
            </tr>
            <tr>
                <td>user name:</td>
                <td><form:input path = "userName" /><br></td>
            </tr>
            <tr>
                <td>password:</td>
                <td><form:input path = "password" type = "password"/><br></td>
            </tr>
            <tr>
                <td></td>
                <td><input type = "submit" value = "submit" /></td>
            </tr>
        </table>
    </form:form>
</body>
</html>
