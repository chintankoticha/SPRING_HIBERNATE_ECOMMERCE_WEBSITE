<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<c:if test="${requestScope.messagelogin!=null}">
		<p>
			<c:out value="${requestScope.messagelogin}"/>
		</p>
	</c:if>

	<a href="user/userregister.htm">Register a new User</a>
	<br />
	
	<a href="seller/sellerregister.htm">Register a new Seller</a>
	<br />

	<h2>Login</h2>
	<form action="user/userlogin.htm" method="post">
	<div>
		<table>
			<tr>
				<td>User Name:</td>
				<td><input name="username" size="30" required="required" /></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" name="password" size="30"
					required="required" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" name="user" value="Login as a customer" /></td>
			</tr>
		</table>
	</div>
	</form>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<form action="seller/sellerlogin.htm" method="post">
	<div>
		<table>
			<tr>
				<td>User Name:</td>
				<td><input name="sellerusername" size="30" required="required"/></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" name="sellerpassword" size="30" required="required"/></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" id="seller" name="seller" value="Login As a seller" /></td>
			</tr>
		</table>
	</div>
	</form>
</body>
</html>