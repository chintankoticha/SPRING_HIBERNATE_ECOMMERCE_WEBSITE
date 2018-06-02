<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script>
	$(document)
			.ready(
					function() {
						$('#list').click(function(event) {
							event.preventDefault();
							$('#products .item').addClass('list-group-item');
						});

						$('#grid').click(
								function(event) {
									event.preventDefault();
									$('#products .item').removeClass(
											'list-group-item');
									$('#products .item').addClass(
											'grid-group-item');
								});

						$(document).on("click", ".btn", function() {
							var productId = this.id;
							$.post("addToCart.htm", {
								productid : productId
							}).done(function(serverdata) {
								alert('PRODUCT ADDED TO CART!!');
							});
						});

						$("#search1")
								.on(
										"click",
										function(event) {
											event.preventDefault();
											var elem = document
													.getElementById('submit1');
											if (elem != null) {
												elem.parentNode
														.removeChild(elem);
											}
											var keycode = (event.keyCode ? event.keyCode
													: event.which);
											var text = $("#search").val();
											$
													.post(
															"updateproductlist.htm",
															{
																text : text
															})
													.done(
															function(serverdata) {
																$("#products")
																		.load(
																				location.href
																						+ " #products>*",
																				"");
																$(
																		"#addmorecode")
																		.append(
																				"<br/><input type='button' id='submit1' name='submit1' value='CLEAR SEARCH FILTER'>");
															});
										});

						$("#addmorecode")
								.on(
										"click",
										"#submit1",
										function() {
											var text = $("#search").val();
											$
													.post(
															"updateproductlistfilter.htm",
															{
																text : text
															})
													.done(
															function(serverdata) {
																$("#products")
																		.load(
																				location.href
																						+ " #products>*",
																				"");
															});
										});
					});
</script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
<link
	href='https://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet" type="text/css"
	href="resources/css/imageslider.css">
<link rel="stylesheet" type="text/css"
	href="resources/css/demoproductview.css">
<style>
.search {
	width: 130px;
	-webkit-transition: width 0.4s ease-in-out;
	transition: width 0.4s ease-in-out;
	box-sizing: border-box;
	border: 2px solid #ccc;
	border-radius: 4px;
	font-size: 16px;
	background-color: white;
}

/* When the input field gets focus, change its width to 100% */
.search:focus {
	width: 50%;
}
</style>

<title>CUSTOMER BUY</title>
</head>
<body>
	<div class="slider">
		<figure>
			<div class="slide">
				<img src="resources/images/image1.PNG">
			</div>

			<div class="slide">
				<img src="resources/images/image2.PNG">
			</div>

			<div class="slide">
				<img src="resources/images/image3.PNG">
			</div>

			<div class="slide">
				<img src="resources/images/image4.PNG">
			</div>

			<div class="slide">
				<img src="resources/images/image5.PNG">
			</div>
		</figure>
	</div>
	<br />
	<br />

	<div class="container">
		<form method="post">
			<input type="text" style="float: left;" id="search" class="search"
				name="search" placeholder="Search.." /><br /> <br /> <input
				type="button" id="search1" value="SEARCH" />
			<div id="addmorecode"></div>
		</form>
		<c:url value="http://localhost:8080/controller/logout.htm" var="myURL">
		</c:url>

		<ul style="float: right;" class="list-inline">
			<li><a href="#" id="home" style="font-size: 18px">HOME</a></li>
			<li><a href="cart.htm" id="cart" style="font-size: 18px">CART/BUY</a></li>
			<li><a href="orderhistory.htm" id="orderhistory"
				style="font-size: 18px">ORDER HISTORY</a></li>
			<li><a href="#" id="messages" style="font-size: 18px">MESSAGES</a></li>
			<li><a style="font-size: 18px" href="${myURL}">LOGOUT</a></li>
		</ul>
		<br /> <br />
		<div class="well well-sm">
			<strong>Category Title</strong>
			<div class="btn-group">
				<a href="#" id="list" class="btn btn-default btn-sm"><span
					class="glyphicon glyphicon-th-list"> </span>List</a> <a href="#"
					id="grid" class="btn btn-default btn-sm"><span
					class="glyphicon glyphicon-th"></span>Grid</a>
			</div>
		</div>

		<div id="products" class="row list-group">
			<c:forEach var="loop" items="${sessionScope.productlist}">
				<div class="item  col-xs-4 col-lg-4">
					<div class="thumbnail">
						<img class="group list-group-image"
							src="http://localhost:8080/controller/user/resources/images/${loop.filename}"
							style="height: 200px; width: 350px; overflow: hidden;" alt="" />
						<div class="caption">
							<h4 class="group inner list-group-item-heading">
								PRODUCT NAME:
								<c:out value="${loop.name}" />
							</h4>
							<p class="group inner list-group-item-text">
								PRODUCT DESCRIPTION:
								<c:out value="${loop.description}" />
							</p>
							<p class="group inner list-group-item-text">
								SOLD BY:
								<c:out value="${loop.seller.name}" />
							</p>
							<div class="row">
								<div class="col-xs-12 col-md-6">
									<p class="lead">
										<c:out value="$${loop.price}" />
									</p>
								</div>
								<div class="col-xs-12 col-md-6">
									<button id="${loop.id}" class="btn btn-success">ADD TO
										CART</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</body>
</html>