<%--suppress ALL --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
	<title>Edit member </title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="../../js/script.js"></script>

</head>
<body>
<div class="row">
	<div class="col-md-2"><%@ include file="../../../index.jsp"%></div>
	<div class="col-md-8">
		<form:form cssclass="form-group" action="/member/updateMember" method="post" commandName="editMember">
			<table class="table table-hover">
				<tr>
					<td >memberId</td>
					<td ><form:input path="memberId" readonly="true"/></td>
					<td ><form:errors path="memberId" cssclass="danger"/></td>
				</tr>
				<tr>
					<td>memberFirstName</td>
					<td><form:input path="memberFirstName"/></td>
					<td><form:errors path="memberFirstName" cssclass="danger"/></td>
				</tr>
				<tr>
					<td>memberLastName</td>
					<td><form:input path="memberLastName"/></td>
					<td><form:errors path="memberLastName" cssclass="danger"/></td>
				</tr>
				<tr>
					<td>memberPhoneNumber</td>
					<td><form:input path="memberPhoneNumber"/></td>
					<td><form:errors path="memberPhoneNumber" cssclass="danger"/></td>
				</tr>
				<tr>
					<td>memberGender</td>
					<td><form:input path="memberGender"/></td>
					<td><form:errors path="memberGender" cssclass="danger"/></td>
				</tr>
				<c:forEach var="m" items="${memberEntertainers}">
					${m.entertainerStageName} <br/>
				</c:forEach>
				<tr>
					<td>
						<input class="btn btn-danger" type="button" value="Delete"
						       onclick="deleteObj('/member/deleteMember?memberId=${editMember.memberId}');">
						<input class="btn btn-success" type="submit" name="" value="Save">
						<input class="btn btn-default" type="button" value="Back" onclick="javascript:go('/member');">
					</td>
				</tr>
			</table>
		</form:form>

	</div>
	<div class="col-md-2"></div>

</div>
</body>
</html>
