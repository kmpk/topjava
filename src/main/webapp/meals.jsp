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
        <th scope="col"></th>
        <th scope="col"></th>
    </tr>
    <jsp:useBean id="meals" scope="request" type="java.util.List"/>
    <c:forEach items="${meals}" var="meal">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr style="color:${meal.excess? "red" : "green"}">
            <td>${TimeUtil.formatDateTime(meal.dateTime)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?command=upd&id=${meal.id}">update</a></td>
            <td><a href="meals?command=del&id=${meal.id}">delete</a></td>
        </tr>
    </c:forEach>
</table>
<form action="meals" method="get">
    <input type="hidden" name="command" value="add">
    <input type="submit" value="add new meal"/>
</form>
</body>
</html>
