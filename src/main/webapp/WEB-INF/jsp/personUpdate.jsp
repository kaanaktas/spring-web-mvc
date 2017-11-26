<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Person Update View</title>

<link rel="stylesheet" type="text/css" media="screen"
	href="<c:url value="/resources/sidebar.css" />" />
<link rel="stylesheet" type="text/css" media="screen"
	href="<c:url value="/resources/bootstrap.min.css" />" />
<script src="<c:url value="/resources/jquery.min.js" />"></script>

</head>
<body>
	<h1>Person Update View</h1>
	<form:form  modelAttribute="person" method="post"
		servletRelativeAction="/mvc/updatePerson">
		<table>
			<tr>
				<td>ID</td>
				<td><form:input path="id" readonly="true" /></td>
			</tr>
			<tr>
				<td>First Name</td>
				<td><form:input path="firstName" /> <form:errors
						path="firstName" /></td>
			</tr>
			<tr>
				<td>Last Name</td>
				<td><form:input path="lastName" /> <form:errors path="lastName" />
				</td>
			</tr>
		</table>
		<form:errors>
		</form:errors>
		<form:button name="Update">Update</form:button>
	</form:form>
	<font color="red"> ${message} </font>
</body>
</html>