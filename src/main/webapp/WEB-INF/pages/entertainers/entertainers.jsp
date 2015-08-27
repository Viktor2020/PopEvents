<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<title>All Entertainers</title>
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
		<%@ include file="searchFormEntertainer.jsp" %>

		<table class="table table-hover">
			<thead>
			<td> Id</td>
			<td> Stage Name</td>
			<td> Street address</td>
			<td> City</td>
			<td> State</td>
			<td> Phone Number</td>
			<td> Web Page</td>
			<td> Email Address</td>
			<td> Date Entered</td>
			</thead>
			<tbody>
			<c:forEach var="a" items="${entertainers}">
				<tr>
					<td> ${a.entertainerId} </td>
					<td> ${a.entertainerStageName} </td>
					<td> ${a.entertainerStreetAddress} </td>
					<td> ${a.entertainerCity} </td>
					<td> ${a.entertainerState} </td>
					<td> ${a.entertainerPhoneNumber} </td>
					<td> ${a.entertainerWebPage} </td>
					<td> ${a.entertainerEmailAddress} </td>
					<td> ${a.entertainerDateEntered} </td>
					<td>
						<a class="btn btn-info"
						   href="${pageContext.request.contextPath}/entertainer/updateEntertainer?entertainerId=${a.entertainerId}">Edit</a>
						<a class="btn btn-danger"
						   href="javascript:deleteObj('/entertainer/deleteEntertainer?entertainerId=${a.entertainerId}');">Delete</a>
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
