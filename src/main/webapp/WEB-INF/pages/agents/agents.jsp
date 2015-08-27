<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<title>All Agents</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="../../js/script.js"></script>
</head>
<body>

<div class="row">
	<div class="col-md-2">
		<%@ include file="../../../index.jsp"%>
	</div>
	<div class="col-md-8">
		<%@ include file="searchFormAgent.jsp" %>

		<table class="table table-hover">
			<thead>
			<td> Id</td>
			<td> First name</td>
			<td> Last name</td>
			<td> Street address</td>
			<td> City</td>
			<td> State</td>
			<td> Phone Number</td>
			<td> Date Hired</td>
			<td> Salary</td>
			<td> Commission Rate</td>
			</thead>
			<tbody>
			<c:forEach var="a" items="${agents}">
				<tr>
					<td> ${a.agentId} </td>
					<td> ${a.agentFirstName} </td>
					<td> ${a.agentLastName} </td>
					<td> ${a.agentStreetAddress} </td>
					<td> ${a.agentCity} </td>
					<td> ${a.agentState} </td>
					<td> ${a.agentPhoneNumber} </td>
					<td> ${a.agentDateHired} </td>
					<td> ${a.agentSalary} </td>
					<td> ${a.agentCommissionRate} </td>
					<td>
						<a class="btn btn-info"
						   href="${pageContext.request.contextPath}/agent/updateAgent?agentId=${a.agentId}">Edit</a>
						<a class="btn btn-danger"
						   href="javascript:deleteObj('/agent/deleteAgent?agentId=${a.agentId}');">Delete</a>
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
