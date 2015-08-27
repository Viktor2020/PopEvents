<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<title>All Members</title>
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
		<%@ include file="searchFormMember.jsp" %>

		<table class="table table-hover">
			<thead>
			<td> Id</td>
			<td> First name</td>
			<td> Last name</td>
			<td> Phone Number</td>
			<td> Gender</td>
			</thead>
			<tbody>
			<c:forEach var="a" items="${members}">
				<tr>
					<td> ${a.memberId} </td>
					<td> ${a.memberFirstName} </td>
					<td> ${a.memberLastName} </td>
					<td> ${a.memberPhoneNumber} </td>
					<td> ${a.memberGender} </td>
					<td>
						<a class="btn btn-info"
						   href="${pageContext.request.contextPath}/member/updateMember?memberId=${a.memberId}">Edit</a>
						<a class="btn btn-danger"
						   href="javascript:deleteObj('/member/deleteMember?memberId=${a.memberId}');">Delete</a>
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
