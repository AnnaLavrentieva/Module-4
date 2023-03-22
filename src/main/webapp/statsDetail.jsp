<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Detail statistic</title>
</head>
<body>
   <h1>Detail statistics by the selected id:</h1>
    <ul>
       <li>Id: ${detail.id}</li>
       <li>Production date: ${detail.date}</li>
       <li>Remaining fuel: ${detail.fuel}</li>
       <li>Spent fuel: ${detail.spentFuel}</li>
       <li>Spent time: ${detail.spentTime} sec.</li>
       <li>Broken microchips: ${detail.brokenMicrochips}</li>
    </ul>
  <form method='get' action='http://localhost:8080/stats'>
        <input type="submit" value="Statistic page">
  </form>
  <form method='get' action='http://localhost:8080'>
          <input type="submit" value="Main page">
  </form>
</body>
</html>