<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" 
integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ADD PRODUCTS</title>
</head>
<body>
	<c:url value="http://localhost:8080/controller/logout.htm" var="myURL">
	</c:url>

	<form>
		<a style="float: right" href="${myURL}">LOGOUT</a>
	</form>
	<c:choose>
		<c:when test="${type eq 'create'}">
			<c:set var="contextPath" value="/create" />
		</c:when>
		<c:otherwise>
			<c:set var="contextPath" value="/update" />
		</c:otherwise>
	</c:choose>
	<c:set var="index" value="${fn:length(seller.products)}"/>
	<% System.out.println("Index value:\t "+pageContext.findAttribute("index")); %>
	<form:form action="AddProducts.htm" method="post" modelattribute="seller" data-toggle="validator" commandName="product">
		<table>
			<tr>
				<td>Posted By</td>
				<td>${postedby}</td>
				<td><input type="hidden" name="postedBy"
					value="${sessionScope.postedby}" /></td>
			</tr>
			<tr>
				<td>Category:</td>
				<td><select multiple required name="categories">
						<c:forEach var="loop" items="${sessionScope.categories}">
							<option value="${loop}">${loop.getTitle()}</option>
						</c:forEach>
				</select></td>
			</tr>
		</table>
		<br />
		<br />
		<div id="products${loop.index}.wrapper">
			<table id="heading" border="1">
				<tr>
					<th>PRODUCT NAME</th>
					<th>PRODUCT DESCRIPTION</th>
					<th>PRODUCT PRICE</th>
					<th>PRODUCT QUANTITY</th>
				</tr>
				<c:forEach items="${seller.products}" var="products" varStatus="loop">
					<tr>
					</tr>
				</c:forEach>
			</table>
		</div>
		<br/>
		<br/>
		<button id="addmore" type="button" data-id="${index}">ADD PRODUCTS</button>
		<input type="submit" value="POST PRODUCTS" />
	</form:form>
	<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
	<script>
	$(document).ready(function(){
		$(document).on('load',function(){
			$('#heading').hide();
		});
		
		 $("#addmore").click( function(e) {
	   	  e.preventDefault();
	   	  alert("ADD MORE CLICKED");
	   	  $("#heading").show();
		  var index =$("#addmore").data('id');
		  alert(index);
	         $(this).before(function() {
	        	 //var index =$(this).data('selector');
	             var html = '<div id="products' + index + '.wrapper" class="hidden">';
	             html +=	'<tr><td><input path="name" id="products' + index + '.name" name="products[' + index + '].name" size="20" pattern="^[_A-z0-9]{1,}$" required="required"/><font color="red"><form:errors path="name"/></font></td>';
	             html+= '<td><input path="description" id="products' + index + '.description " name="products[' + index + '].description" pattern="^[_\\sA-z0-9]{1,}$" size="20" required="required"/><font color="red"><form:errors path="description"/></font></td>';
	             html+= '<td><input path="price" id="products' + index + '.price" name="products[' + index + '].price" size="20" pattern="^[0-9]*\.?[0-9]+$" required="required"/><font color="red"><form:errors path="price"/></font></td>';
	             html+= '<td><input path="quantity" id="products' + index + '.quantity" name="products[' + index + '].quantity" size="19" pattern="^[0-9]+$" required="required"/><font color="red"><form:errors path="quantity"/></font></td>';
	             html+= '</tr></div>';
	             console.log("%%%%%%%%%%%%%%%%%%%%%%"+index);
	             return html;
	         });
	         $('#products' + index+ '\\.wrapper').show();
	         index++;
	         alert(index);
	         return false;
	     });
	}); 
	
</script>
</body>
</html>