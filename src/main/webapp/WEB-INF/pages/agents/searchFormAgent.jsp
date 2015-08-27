
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Search Agent</title>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="../../js/script.js"></script>
</head>
<body>
<form action="${pageContext.request.contextPath}/agent/searchAgents">
  <table >
    <tr>
      <td>Enter Contact Name</td>
      <td><label>
        <input type="text" name="agentFirstName"/>
      </label>
        <input class="btn btn-info" type="submit" value="Search"/>
        <input class="btn btn-primary" type="button" value="New Agent" onclick="go('/agent/saveAgent');"/>
      </td></tr>
  </table>
</form>
</body>
</html>
