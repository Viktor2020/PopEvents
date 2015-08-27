<%--suppress ALL --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
	<title>Edit agent </title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="../../js/script.js"></script>
</head>
<body>
<div class="row">
	<div class="col-md-2"><%@ include file="../../../index.jsp"%></div>
	<div class="col-md-8">
		<form:form cssclass="form-group" action="/agent/updateAgent" method="post" commandName="editAgent">
			<table class="table table-hover">
				<tr>
					<td >agentId</td>
					<td ><form:input path="agentId" readonly="true"/></td>
					<td ><form:errors path="agentId" cssclass="danger"/></td>
				</tr>
				<tr>
					<td >agentFirstName</td>
					<td><form:input path="agentFirstName"/></td>
					<td ><form:errors path="agentFirstName" cssclass="danger"/></td>
				</tr>
				<tr>
					<td >agentLastName</td>
					<td><form:input path="agentLastName"/></td>
					<td ><form:errors path="agentLastName" cssclass="danger"/></td>
				</tr>
				<tr>
					<td >agentStreetAddress</td>
					<td><form:input path="agentStreetAddress"/></td>
					<td ><form:errors path="agentStreetAddress" cssclass="danger"/></td>
				</tr>
				<tr>
					<td >agentCity</td>
					<td><form:input path="agentCity"/></td>
					<td ><form:errors path="agentCity" cssclass="danger"/></td>
				</tr>
				<tr>
					<td >agentState</td>
					<td><form:input path="agentState"/></td>
					<td ><form:errors path="agentState" cssclass="danger"/></td>
				</tr>
				<tr>
					<td >agentPhoneNumber</td>
					<td><form:input path="agentPhoneNumber"/></td>
					<td ><form:errors path="agentPhoneNumber" cssclass="danger"/></td>
				</tr>
				<tr>
					<td >agentDateHired</td>
					<td><form:input path="agentDateHired"/></td>
					<td ><form:errors path="agentDateHired" cssclass="danger"/></td>
				</tr>
				<tr>
					<td >agentSalary</td>
					<td><form:input path="agentSalary"/></td>
					<td ><form:errors path="agentSalary" cssclass="danger"/></td>
				</tr>
				<tr>
					<td >agentCommissionRate</td>
					<td><form:input path="agentCommissionRate"/></td>
					<td ><form:errors path="agentCommissionRate" cssclass="danger"/></td>
				</tr>
				<tr>
					<td >
						<input class="btn btn-danger" type="button" value="Delete" onclick="deleteObj('/agent/deleteAgent?agentId=${editAgent.agentId}');">
						<input class="btn btn-success" type="submit" name="" value="Save">
						<input class="btn btn-default" type="button" value="Back" onclick="javascript:go('/agent');">
					</td>
				</tr>
			</table>
		</form:form></div>
	<div class="col-md-2"></div>

</div>
</body>
</html>
