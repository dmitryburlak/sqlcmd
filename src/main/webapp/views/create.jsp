<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>sqlcmd</title>
</head>
<body>
    <form:form action = "create" method = "POST" modelAttribute = "create">
            <table>
                <tr>
                    <td>new table name:</td>
                    <td><form:input path = "tableName" /><br></td>
                </tr>
                <tr>
                    <td>column name(PK):</td>
                    <td><form:input path = "columnPk" /><br></td>
                </tr>
                <tr>
                    <td>column1 name:</td>
                    <td><form:input path = "columnOne" /><br></td>
                </tr>
                <tr>
                    <td>column2 name:</td>
                    <td><form:input path = "columnTwo" /><br></td>
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
