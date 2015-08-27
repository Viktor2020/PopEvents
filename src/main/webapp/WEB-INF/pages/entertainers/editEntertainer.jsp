<%--suppress ALL --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
	<title>Edit entertainer </title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="../../js/script.js"></script>

</head>
<body>
<div class="row">
	<div class="col-md-2"><%@ include file="../../../index.jsp"%></div>
	<div class="col-md-8">
		<form:form cssclass="form-group" action="/entertainer/updateEntertainer" method="post" commandName="editEntertainer">
			<table class="table table-hover">
				<tr>
					<td >entertainerId</td>
					<td ><form:input path="entertainerId" readonly="true"/></td>
					<td ><form:errors path="entertainerId" cssclass="danger"/></td>
				</tr>
				<tr>
					<td>entertainerStageName</td>
					<td><form:input path="entertainerStageName"/></td>
					<td><form:errors path="entertainerStageName" cssclass="danger"/></td>
				</tr>
				<tr>
					<td>entertainerStreetAddress</td>
					<td><form:input path="entertainerStreetAddress"/></td>
					<td><form:errors path="entertainerStreetAddress" cssclass="danger"/></td>
				</tr>
				<tr>
					<td>entertainerCity</td>
					<td><form:input path="entertainerCity"/></td>
					<td><form:errors path="entertainerCity" cssclass="danger"/></td>
				</tr>
				<tr>
					<td>entertainerState</td>
					<td><form:input path="entertainerState"/></td>
					<td><form:errors path="entertainerState" cssclass="danger"/></td>
				</tr>
				<tr>
					<td>entertainerPhoneNumber</td>
					<td><form:input path="entertainerPhoneNumber"/></td>
					<td><form:errors path="entertainerPhoneNumber" cssclass="danger"/></td>
				</tr>
				<tr>
					<td>entertainerWebPage</td>
					<td><form:input path="entertainerWebPage"/></td>
					<td><form:errors path="entertainerWebPage" cssclass="danger"/></td>
				</tr>
				<tr>
					<td>entertainerEmailAddress</td>
					<td><form:input path="entertainerEmailAddress"/></td>
					<td><form:errors path="entertainerEmailAddress" cssclass="danger"/></td>
				</tr>
				<tr>
					<td>entertainerDateEntered</td>
					<td><form:input path="entertainerDateEntered"/></td>
					<td><form:errors path="entertainerDateEntered" cssclass="danger"/></td>
				</tr>

				<c:forEach var="m" items="${entertainerMusicalStyles}">
					${m.musicalStyleName} <br/>
				</c:forEach>

				<c:forEach var="m" items="${entertainerMembers}">
					${m.memberFirstName} <br/>
				</c:forEach>
				<tr>
					<td>
						<input class="btn btn-danger" type="button" value="Delete"
						       onclick="deleteObj('/entertainer/deleteEntertainer?entertainerId=${editEntertainer.entertainerId}');">
						<input class="btn btn-success" type="submit" name="" value="Save">
						<input class="btn btn-default" type="button" value="Back" onclick="javascript:go('/entertainer');">
					</td>
				</tr>
			</table>
		</form:form>

	</div>
	<div class="col-md-2"></div>

</div>
</body>
</html>
