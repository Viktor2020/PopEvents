<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Pop Events</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>

<form action="${pageContext.request.contextPath}/agent" method="get">
    <input  class="btn btn-default" type="submit" value="get agents"/>
</form>
<form action="${pageContext.request.contextPath}/customer" method="get">
    <input class="btn btn-default" type="submit" value="get customer"/>
</form>
<form action="${pageContext.request.contextPath}/musicalStyle" method="get">
    <input class="btn btn-default" type="submit" value="get musicalStyle"/>
</form>
<form action="${pageContext.request.contextPath}/member" method="get">
    <input class="btn btn-default" type="submit" value="get member"/>
</form>
<form action="${pageContext.request.contextPath}/entertainer" method="get">
    <input class="btn btn-default" type="submit" value="get entertainer"/>
</form>
<form action="${pageContext.request.contextPath}/engagement" method="get">
    <input class="btn btn-default" type="submit" value="get engagement"/>
</form>
</body>
</html>