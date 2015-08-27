<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<title>All Customers</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="../../js/script.js"></script>
</head>
<body>

<div class="row">
	<div class="col-md-1">
		<%@ include file="../../../index.jsp"%>
	</div>
	<div class="col-md-9">
		<%@ include file="searchFormCustomer.jsp" %>

		<table class="table table-hover">
			<thead>
			<td> Id</td>
			<td> First name</td>
			<td> Last name</td>
			<td> Street address</td>
			<td> City</td>
			<td> State</td>
			<td> Phone Number</td>
			</thead>
			<tbody>
			<c:forEach var="a" items="${customers}">
				<tr>
					<td> ${a.customerId} </td>
					<td> ${a.customerFirstName} </td>
					<td> ${a.customerLastName} </td>
					<td> ${a.customerStreetAddress} </td>
					<td> ${a.customerCity} </td>
					<td> ${a.customerState} </td>
					<td> ${a.customerPhoneNumber} </td>
					<td>
						<a class="btn btn-info"
						   href="${pageContext.request.contextPath}/customer/updateCustomer?customerId=${a.customerId}">Edit</a>
						<a class="btn btn-danger"
						   href="javascript:deleteObj('/customer/deleteCustomer?customerId=${a.customerId}');">Delete</a>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="col-md-1"></div>
</div>

</body>
</html>
