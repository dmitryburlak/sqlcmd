<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>sqlcmd</title>
</head>
<body>
список команд:<br>
<br>
connect|databaseName|userName|password<br>
для подключения к базе данных, с которой будем работать<br>
<br>
help<br>
список команд<br>
<br>
tables<br>
список таблиц<br>
<br>
find|tableName<br>
посмотреть содержимое таблицы 'tableName'<br>
<br>
create|tableName|column1(PK)|column2|...|columnN<br>
создание таблицы 'tableName'<br>
<br>
drop|tableName<br>
удаление таблицы 'tableName'<br>
<br>
clear|tableName<br>
очистка таблицы 'tableName'<br>
<br>
insert|tableName|column1|value1|column2|value2|...|columnN|valueN<br>
создание записи в таблице 'tableName'<br>
<br>
delete|tableName|column|value<br>
создание записи в таблице 'tableName' из колонки 'column' значение 'value'<br>
<br>
update|tableName|id|column|newvalue<br>
обновление записи таблице 'tableName' №id в колонке 'column' запись обновлена<br>
<br>
<footer>
    <a href="menu">menu</a><br>
</footer>

</body>
</html>
