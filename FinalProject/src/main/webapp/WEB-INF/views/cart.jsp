<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"
	integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
	crossorigin="anonymous"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script src="resources/js/cartoperations.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script>
	$(document).ready(function() {
		$(document).on("click", "#buynow", function(event) {
			var a = 1;
			$("#table tr").not(':first').not(':last').each(function() {
				var cell1 = $(this).find('td:eq(3)').find('input').val();
				var cell2 = $(this).find('td:eq(4)').find('input').val();
				if (cell2 <= 0) {
					alert('Quantity should be more than 0!!!');
					event.preventDefault();
					a = 0;
				} else if (cell1 - cell2 < 0) {
					alert('Quantity selected is more than available!!');
					event.preventDefault();
					a = 0;
				}
			});
			if (a == 1) {
				$.post("updatequantityproducts.htm").done(function(serverdata) {
					alert('Back from db!!');
				});
			}
		});
	});
</script>
<title>USER CART</title>
</head>
<body>
	<c:url value="http://localhost:8080/controller/logout.htm" var="myURL">
	</c:url>

	<form>
		<a style="float: right" href="${myURL}">LOGOUT</a>
	</form>

	<c:choose>
		<c:when test="${not empty sessionScope.cartlist}">
			<c:set var="index" value="${0}" />
			<form id="buyform" data-toggle="validator" method="post">
				<h3>PLEASE FIND THE LIST OF PRODUCTS IN YOUR CART BELOW:</h3>
				<table id="table" border="1">
					<tr>
						<td><input type="text" size="20" disabled
							value="PRODUCT NAME" /></td>
						<td><input type="text" size="20" disabled
							value="PRODUCT DESCRIPTION" /></td>
						<td><input type="text" size="20" disabled value="PRICE" /></td>
						<td><input type="text" size="20" disabled
							value="AVAILABLE QUANTITY" /></td>
						<td><input type="text" size="20" disabled value="QUANTITY" /></td>
						<td><input type="text" size="20" disabled value="UPDATE" /></td>
						<td><input type="text" size="20" disabled value="DELETE" /></td>
					</tr>
					<c:forEach var="loop" items="${sessionScope.cartlist}">
						<tr id="${loop.product.id}" data-id="${index+1}">
							<c:set var="index" value="${index+1}" />
							<td><input type="text" name="name" size="20" disabled
								value="${loop.product.name}" /></td>
							<td><input type="text" disabled size="20"
								value="${loop.product.description}" /></td>
							<td><input type="text" disabled name="price" size="20"
								value="${loop.product.price}" /></td>
							<td><input type="text" disabled name="pq" size="20"
								value="${loop.product.quantity}" /></td>
							<td><input type="text" pattern="^[0-9]+$"
								data-quantity="quantity${index}" size="20"
								value="${loop.quantity}" /></td>
							<td><input type="button" class="update"
								data-update="${loop.product.id}" size="20" name="update"
								value="UPDATE" /></td>
							<td><input type="button" data-delete="${loop.product.id}"
								size="20" class="delete" id="${index+1}" value="DELETE" /></td>
						</tr>
					</c:forEach>
					<tr>
						<td><input type="text" size="20" disabled
							value="YOUR TOTAL PRICE:" /></td>
						<td><input type="text" name="totalprice" disabled
							value="$${sessionScope.totalprice}"></td>
						<td><a href="buypage.htm" id="buynow" name="submit">BUY
								NOW</a></td>
					</tr>
				</table>
			</form>
		</c:when>
		<c:otherwise>
			<h3>NO PRODUCTS HAVE BEEN ADDED AS OF NOW!</h3>
		</c:otherwise>
	</c:choose>
</body>
</html>