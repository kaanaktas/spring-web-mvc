<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Person List View</title>

<link rel="stylesheet" type="text/css" media="screen"
	href="<c:url value="/resources/sidebar.css" />" />
<link rel="stylesheet" type="text/css" media="screen"
	href="<c:url value="/resources/bootstrap.min.css" />" />
<script src="<c:url value="/resources/jquery.min.js" />"></script>

</head>
<body>
	<h1>Person List View</h1>
	<a href = "<%=request.getContextPath()%>/mvc/createPerson">Create</a>
	<br/>
	<br/>
	<table border="1">
		<thead>
			<tr>
				<td align="center">ID</td>
				<td align="center">First Name</td>
				<td align="center">Last Name</td>
				<td align="center" colspan="2">Action</td>
			</tr>
		</thead>
		<c:forEach items="${persons}" var="person">
			<tr>
				<td>${person.id}</td>
				<td>${person.firstName}</td>
				<td>${person.lastName}</td>
				<td>
					<form action="<%=request.getContextPath()%>/mvc/updatePerson/${person.id}" method="get">
						<input type="submit" value="Update">
					</form>
				</td>
				<td>
					<form action="<%=request.getContextPath()%>/mvc/deletePerson/${person.id}" method="get">
						<input type="submit" value="Delete">
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>
	<br />
	<font color="blue"> ${message} </font>
</body>
</html>