<%--suppress XmlDuplicatedId --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Add musicalStyle</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="../../js/script.js"></script>
</head>
<body>
<div class="row">
	
</div>
<div class="row">
	<div class="col-md-2"><%@ include file="../../../index.jsp"%></div>
	<div class="col-md-6">
		<form:form action="/musicalStyle/saveMusicalStyle" method="post" commandName="newMusicalStyle">
			<table class="table table-hover">
				<tr>
					<td>musicalStyleFirstName</td>
					<td><form:input path="musicalStyleName"/></td>
					<td><form:errors path="musicalStyleName" cssclass="danger"/></td>
				</tr>
				<tr>
					<td>
						<input class="btn btn-success" type="submit" name="" value="Save">
						<input class="btn btn-info" type="reset" name="" value="Reset">
						<input class="btn btn-default" type="button" value="Back" onclick="go('/musicalStyle');">
					</td>
				</tr>
			</table>
		</form:form>
	</div>
	<div class="col-md-2"></div>
</div>

</body>
</html>
