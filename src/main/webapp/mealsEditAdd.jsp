<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<c:choose>
    <c:when test="${param.command eq 'add'}">
        <c:set var="title" scope="page" value="Add meal"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" scope="page" value="Edit meal"/>
    </c:otherwise>
</c:choose>
<head>
    <title>${title}</title>
</head>
<style>
    input {
        margin: 5px;
    }
</style>
<body>
<h3><a href="index.html">Home</a></h3>
<hr/>
<h2>${title}</h2>
<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
<form method="post" action="meals">
    <input name="id" type="hidden" value="${meal.hasId()? meal.id : ""}">
    <div>
        <label>DateTime:
            <input name="dateTime" type="datetime-local" value="${meal.dateTime}"/>
        </label>
    </div>
    <div>
        <label>Description:
            <input name="description" type="text" maxlength="200" value="${meal.description}"/>
        </label>
    </div>
    <div>
        <label>Calories:
            <input name="calories" type="number" min="1" max="10000" value="${meal.calories}"/>
        </label>
    </div>
    <div>
        <input type="submit" value="confirm">
        <input type="reset" value="reset">
    </div>
</form>
</body>
</html>