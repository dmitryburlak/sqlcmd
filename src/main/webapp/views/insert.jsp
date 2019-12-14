<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>sqlcmd</title>
</head>
<body>
    <form:form action = "insert" method = "POST" modelAttribute="insert">
        <table>
            <tr>
                <td>table name:</td>
                <td><form:input path="tableName" /><br></td>
            </tr>
            <tr>
                <td>column1 name:</td>
                <td><form:input path = "column" /><br></td>
            </tr>
            <tr>
                <td>column1 value:</td>
                <td><form:input path = "value" /><br></td>
            </tr>
            <tr>
                <td>column2 name:</td>
                <td><form:input path = "columnsecond" /><br></td>
            </tr>
            <tr>
                <td>column2 value:</td>
                <td><form:input path = "valuesecond" /><br></td>
            </tr>
            <tr>
                <td></td>
                <td><input type = "submit" value = "submit" /></td>
            </tr>
        </table>
    </form:form>
    <tr>
        <td>${message}</td>
        <td><a href="menu">menu</a></td>
    </tr>
</body>
</html>
