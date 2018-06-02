<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" 
integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.2.1.min.js" integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
	crossorigin="anonymous"></script>
<title>Register Yourself</title>
</head>
<body>

	<h2>Register a New Seller</h2>

	<form:form action="registerseller.htm" commandName="seller" data-toggle="validator"
		method="POST">
		<table>
			<tr>
				<td>Name:</td>
				<td><form:input path="name" size="30" pattern="^[_A-z0-9]{1,}$" required="required" /> <font
					color="red"><form:errors path="name" /></font></td>
			</tr>

			<tr>
				<td>User Name:</td>
				<td><form:input path="username" size="30" pattern="^[_A-z0-9]{1,}$" required="required" />
					<font color="red"><form:errors path="username" /></font></td>
			</tr>

			<tr>
				<td>Password:</td>
				<td><form:password path="password" size="30" pattern="^[_@.A-z0-9]{1,}$" required="required" /> 
				<font color="red"><form:errors path="password" /></font></td>
			</tr>

			<tr>
				<td>Contact Number:</td>
				<td><form:input path="contactnumber" size="30" pattern="^[0-9]{10}$" required="required" /> 
				<font color="red"><form:errors path="contactnumber" /></font></td>
			</tr>

			<tr>
				<td>Email Id:</td>
				<td><form:input path="emailAddress" size="30" required="required" /> 
				<font color="red"><form:errors path="emailAddress" /></font></td>
			</tr>

			<tr>
				<td colspan="2"><input type="submit" value="Register Seller" /></td>
			</tr>
		</table>

	</form:form>
	<c:if test="${requestScope.message!=null}">
		<span style:"font-size:12;color:red">
			<c:out value="${requestScope.message}"/>
			<br/><br/>
			<a href="http://localhost:8080/controller/home.htm">CLICK HERE TO GO BACK TO HOMEPAGE!!</a>
		</span>
	</c:if>

</body>
</html>