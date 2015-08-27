<%--suppress XmlDuplicatedId --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Add customer</title>
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
		<form:form action="/customer/saveCustomer" method="post" commandName="newCustomer">
			<table class="table table-hover">
				<tr>
					<td>customerFirstName</td>
					<td><form:input path="customerFirstName"/></td>
					<td><form:errors path="customerFirstName" cssclass="danger"/></td>
				</tr>
				<tr>
					<td>customerLastName</td>
					<td><form:input path="customerLastName"/></td>
					<td><form:errors path="customerLastName" cssclass="danger"/></td>
				</tr>
				<tr>
					<td>customerStreetAddress</td>
					<td><form:input path="customerStreetAddress"/></td>
					<td><form:errors path="customerStreetAddress" cssclass="danger"/></td>
				</tr>
				<tr>
					<td>customerCity</td>
					<td><form:input path="customerCity"/></td>
					<td><form:errors path="customerCity" cssclass="danger"/></td>
				</tr>
				<tr>
					<td>customerState</td>
					<td><form:input path="customerState"/></td>
					<td><form:errors path="customerState" cssclass="danger"/></td>
				</tr>
				<tr>
					<td>customerPhoneNumber</td>
					<td><form:input path="customerPhoneNumber"/></td>
					<td><form:errors path="customerPhoneNumber" cssclass="danger"/></td>
				</tr>
				<%--<tr>--%>
					<%--<td>customerDateHired</td>--%>
					<%--<td><form:input type="date" path="customerDateHired"/></td>--%>
					<%--<td><form:errors path="customerDateHired" cssclass="danger"/></td>--%>
				<%--</tr>--%>
				<%--<tr>--%>
					<%--<td>customerSalary</td>--%>
					<%--<td><form:input path="customerSalary"/></td>--%>
					<%--<td><form:errors path="customerSalary" cssclass="danger"/></td>--%>
				<%--</tr>--%>
				<%--<tr>--%>
					<%--<td>customerCommissionRate</td>--%>
					<%--<td><form:input path="customerCommissionRate"/></td>--%>
					<%--<td><form:errors cssclass="danger" path="customerCommissionRate"/></td>--%>
				<%--</tr>--%>
				<tr>
					<td>
						<input class="btn btn-success" type="submit" name="" value="Save">
						<input class="btn btn-info" type="reset" name="" value="Reset">
						<input class="btn btn-default" type="button" value="Back" onclick="go('/customer');">
					</td>
				</tr>
			</table>
		</form:form>
	</div>
	<div class="col-md-2"></div>
</div>

</body>
</html>
