<%--suppress ALL --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
	<title>Edit customer </title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="../../js/script.js"></script>

</head>
<body>
<div class="row">
	<div class="col-md-2"><%@ include file="../../../index.jsp"%></div>
	<div class="col-md-8">
		<form:form cssclass="form-group" action="/customer/updateCustomer" method="post" commandName="editCustomer">
			<table class="table table-hover">
				<tr>
					<td >customerId</td>
					<td ><form:input path="customerId" readonly="true"/></td>
					<td ><form:errors path="customerId" cssclass="danger"/></td>
				</tr>
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

				<c:forEach var="m" items="${customerMusicalStyles}">
					${m.musicalStyleName} <br/>
				</c:forEach>
				<tr>
					<td>
						<input class="btn btn-danger" type="button" value="Delete"
						       onclick="deleteObj('/customer/deleteCustomer?customerId=${editCustomer.customerId}');">
						<input class="btn btn-success" type="submit" name="" value="Save">
						<input class="btn btn-default" type="button" value="Back" onclick="javascript:go('/customer');">
					</td>
				</tr>
			</table>
		</form:form>
		<%--<form action="${pageContext.request.contextPath}/customer/customerAddMusicalStyle?customerId=${editCustomer.customerId}&musicalStyleName=${editCustomerAddMusicalStyle.musicalStyleName}"--%>
		      <%--method="get" commandName="editCustomerAddMusicalStyle">--%>
			<%--<input path="musicalStyleName"/>--%>
			<%--<errors path="musicalStyleName" class="danger"/>--%>
			<%--<input class="btn btn-info" type="submit" value="Add Musical style">--%>
		<%--</form>--%>

	</div>
	<div class="col-md-2"></div>

</div>
</body>
</html>
