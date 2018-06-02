<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" 
integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.2.1.min.js" integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
	crossorigin="anonymous"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script>$(document).ready(function(){
	 $(document).on('click', '.delete', function(e) {
         e.preventDefault();	
		//alert('INSIDE DELETEPRODUCTS.JS!!!');
		var productid = $(this).parent().parent().data('id');
		var row =  $("#"+productid).fadeOut(3000);
		//alert(productid);
		$.post( "deleteproduct.htm", { product: productid })
		  .done(function(serverdata) {
			  //alert('Deleted from Database!!');
		  });
	});
});</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>UPDATE PRODUCTS</title>
</head>
<body>
	<c:url value="http://localhost:8080/controller/logout.htm" var="myURL">
	</c:url>

	<form>
		<a style="float: right" href="${myURL}">LOGOUT</a>
	</form>
	<c:choose>
		<c:when test="${sessionScope.noofproducts>0}">
			<h3>Please find the list of your posted products below:</h3>
			<form:form commandName="product" method="post" data-toggle="validator"
				enctype="multipart/form-data">
				<table id="table" border="1">
					<th>NAME</th>
					<th>DESCRIPTION</th>
					<th>PRICE</th>
					<th>QUANTITY</th>
					<th>SUPPORTING IMAGE</th>
					<c:forEach var="loop" items="${sessionScope.products}">
						<tr id="${loop.id}" data-id="${loop.id}">
							<td><form:input type="text" path="name" pattern="^[_A-z0-9]{1,}$" size="30"
									required="required" value="${loop.name}"/></td>
							<td><form:input type="text" path="description" pattern="^[A-z0-9]{1,}$" size="30"
									required="required" value="${loop.description}"/></td>
							<td><form:input type="text" path="price" size="30"
									required="required" value="${loop.price}" pattern="^[0-9]*\.?[0-9]+$"/></td>
							<td><form:input type="text" path="quantity" size="30" pattern="^[0-9]+$"
									required="required" value="${loop.quantity}"/></td>
							<td><a href="viewandupdateproduct.htm?id=${loop.id}">UPDATE DATA</a></td>
							<td><input type="button" class="delete" value="DELETE"></td>		
						</tr>
					</c:forEach>
				</table>
			</form:form>
		</c:when>
		<c:otherwise>
			<h2>Well, you do not have any products posted as of now!!</h2>
			<a href="AddProducts.htm">CLICK HERE TO POST SOME OF YOUR
				PRODUCTS!!</a>
			<br />
		</c:otherwise>
	</c:choose>

</body>
</html>