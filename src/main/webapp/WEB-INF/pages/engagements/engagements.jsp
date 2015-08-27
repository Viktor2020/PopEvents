<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<title>All Engagements</title>
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
		<%@ include file="searchFormEngagement.jsp" %>

		<table class="table table-hover">
			<thead>
			<td> Id</td>
			<td> StartDate</td>
			<td> EndDate</td>
			<td> Price</td>
			</thead>
			<tbody>
			<c:forEach var="a" items="${engagements}">
				<tr>
					<td> ${a.engagementId } </td>
					<td> ${a.engagementStartDate } </td>
					<td> ${a.engagementEndDate } </td>
					<td> ${a.engagementPrice } </td>
					<td>
						<a class="btn btn-info"
						   href="${pageContext.request.contextPath}/engagement/updateEngagement?engagementId=${a.engagementId}">Edit</a>
						<a class="btn btn-danger"
						   href="javascript:deleteObj('/engagement/deleteEngagement?engagementId=${a.engagementId}');">Delete</a>
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
