<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>VIEW AND UPDATE PRODUCT</title>
</head>
<body>
	<c:url value="http://localhost:8080/controller/logout.htm" var="myURL">
	</c:url>

	<form>
		<a style="float: right" href="${myURL}">LOGOUT</a>
	</form>
	<h3>VIEW AND UPDATE PRODUCT</h3>
	<c:if test="${not empty sessionScope.productupdate}">
		<c:out value="${sessionScope.productupdate}" />
	</c:if>
	<%
		request.getSession().setAttribute("productupdate", null);
	%>
	<form:form commandName="product" method="post" data-toggle = "validator"
		enctype="multipart/form-data">
		<table id="table">
			<tr>
				<td>NAME:</td>
				<td><form:input type="text" path="name" name="name" size="30" pattern="^[_A-z0-9]{1,}$" 
						required="required" value="${product.name}" /></td>
			</tr>
			<tr>
				<td>DESCRIPTION:</td>
				<td><form:input type="text" path="description"
						name="description" size="30" required="required" pattern="^[A-z0-9]{1,}$" 
						value="${product.description}" /></td>
			</tr>
			<tr>
				<td>PRICE:</td>
				<td><form:input type="text" path="price" name="price" size="30" pattern="^[0-9]*\.?[0-9]+$"
						required="required" value="${product.price}" /></td>
			</tr>
			<tr>
				<td>QUANTITY:</td>
				<td><form:input type="text" path="quantity" name="quantity" pattern="^[0-9]+$"
						size="30" required="required" value="${product.quantity}" /></td>
			</tr>
			<tr>
				<td>SUPPORTING IMAGE:</td>
				<td><input type="file" name="photo"
					accept="image/x-png,image/gif,image/jpeg" required
					value="${product.photo}"></td>
			</tr>
			<tr>
				<td><input type="submit" name="submit" value="UPDATE"></td>
			</tr>
		</table>
	</form:form>
</body>
</html>