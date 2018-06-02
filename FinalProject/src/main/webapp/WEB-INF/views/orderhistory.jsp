<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ORDER HISTORY</title>
</head>
<body>
	<c:url value="http://localhost:8080/controller/logout.htm" var="myURL">
	</c:url>

	<form>
		<a style="float: right" href="${myURL}">LOGOUT</a>
	</form>
	<c:choose>
		<c:when test="${!empty ordermap}">
			<c:forEach items="${ordermap}" var="entry">
				<c:set var="total" value="${0}" />
				<c:out value="Order ID: ${entry.key}" />
				<br />
				<table id="table" border="1" cellpadding="5">
					<tr>
						<th>Product Name</th>
						<th>Quantity</th>
						<th>Total Price</th>
						<th>Date</th>
						<th>Seller Name</th>
						<th>Action</th>
					</tr>
					<c:set var="view_pdf" value="true" />
					<c:forEach var="order" items="${entry.value}">
						<tr id="row-${order.id}" data-id="${order.id}">
							<td>${order.product.name}</td>
							<td>${order.quantity}</td>
							<fmt:parseNumber var="i" type="number"
								value="${order.product.price}" />
							<td><c:set var="totalPrice" value="${order.quantity * i}" />${order.quantity * i}</td>
							<td><fmt:formatDate type="date"
									value="${order.creationDate}" /></td>
							<td>${order.product.seller.name}</td>
							<td class="action_${order.id}"><c:if
									test="${view_pdf == 'true'}">
									<a href="receipt.htm?id=${order.id}" class="view_pdf">View
										Receipt</a>
								</c:if></td>
							<c:set var="total" value="${total+totalPrice}" />
							<c:set var="view_pdf" value="false" />
						</tr>
					</c:forEach>
				</table>
				<h3>
					Total Price: $
					<c:out value="${total}" />
				</h3>
				<br />
			</c:forEach>
		</c:when>
		<c:otherwise>
			<c:out value="No Orders" />
		</c:otherwise>
	</c:choose>
</body>
</html>