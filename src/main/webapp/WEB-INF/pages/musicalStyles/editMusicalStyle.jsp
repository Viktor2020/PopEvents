<%--suppress ALL --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
	<title>Edit musicalStyle </title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="../../js/script.js"></script>

</head>
<body>
<div class="row">
	<div class="col-md-2"><%@ include file="../../../index.jsp"%></div>
	<div class="col-md-8">
		<form:form cssclass="form-group" action="/musicalStyle/updateMusicalStyle" method="post" commandName="editMusicalStyle">
			<table class="table table-hover">
				<tr>
					<td >musicalStyleId</td>
					<td ><form:input path="musicalStyleId" readonly="true"/></td>
					<td ><form:errors path="musicalStyleId" cssclass="danger"/></td>
				</tr>
				<tr>
					<td>musicalStyleFirstName</td>
					<td><form:input path="musicalStyleName"/></td>
					<td><form:errors path="musicalStyleName" cssclass="danger"/></td>
				</tr>
				<tr>
					<td>
						<input class="btn btn-danger" type="button" value="Delete"
						       onclick="deleteObj('/musicalStyle/deleteMusicalStyle?musicalStyleId=${editMusicalStyle.musicalStyleId}');">
						<input class="btn btn-success" type="submit" name="" value="Save">
						<input class="btn btn-default" type="button" value="Back" onclick="javascript:go('/musicalStyle');">
					</td>
				</tr>
			</table>
		</form:form>
	  
	</div>
	<div class="col-md-2"></div>

</div>
</body>
</html>
