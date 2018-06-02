<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Home</title>
</head>
<body>
	<c:url value="http://localhost:8080/controller/logout.htm" var="myURL">
	</c:url>

	<form>
		<a style="float: right" href="${myURL}">LOGOUT</a>
	</form>
	<h2>Welcome Admin!!</h2>
	
	<a href="validatesellers.htm">Validate Sellers</a>
	<br />

	<a href="AddCategories.htm">Add Product Categories</a>
	<br />
</body>
</html>