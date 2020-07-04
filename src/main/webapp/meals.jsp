<%@ page contentType="text/html;charset=UTF-8" import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<style>
    table, th, td {
        border: 1px solid black;
        border-collapse: collapse;
    }
</style>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <caption>Таблица со всей едой</caption>
    <tr>
        <th scope="col">Дата \ Время</th>
        <th scope="col">Описание</th>
        <th scope="col">Калории</th>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <tr style="background-color:${meal.isExcess()? "red" : "green"}">
            <td>${TimeUtil.formatDateTime(meal.getDateTime())}</td>
            <td>${meal.getDescription()}</td>
            <td>${meal.getCalories()}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
