<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>General statistic</title>
</head>
<body>
    <h1>General details statistic</h1>
       <h3>Total amount of:</h3>
        <ul>
            <li>Details: ${stats.totalAmountOfDetails}</li>
            <li>Broken microchips: ${stats.totalAmountOfBrokenMicrochips}</li>
            <li>Extracted fuel: ${stats.totalExtractedFuel}</li>
        </ul>
    <h1>Choose id:</h1>
    <form method='get' action='stats/'>
            <select name="id">
                <c:forEach items="${allId}" var="id">
                   <option value="${id}">${id}</option>
                </c:forEach>
            </select>
            <input type="submit" value="Show Detail Statistic">
    </form>
    <form method='get' action='index.html'>
        <input type="submit" value="Main page">
    </form>
</body>
</html>