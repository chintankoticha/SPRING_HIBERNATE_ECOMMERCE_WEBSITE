<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Validate New Sellers</title>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js">
</script>
<script>
$(document).ready(function(){
	$('#submit').click(function(){	
		var sellername = $('#sellername option:selected').val();
		alert(sellername);
		$.post( "sellerlist.htm", { seller: sellername })
		  .done(function(serverdata) {
		    var obj = jQuery.parseJSON(serverdata);
		    $("#displaysellerdetails").text("SELLER NAME:\t"+obj.name+"\nSELLER EMAIL:\t"+obj.emailAddress+"\nSELLER CONTACT NUMBER:\t"+obj.contactnumber+"\nSELLER USERNAME:\t"+obj.username);
		    $("#addmorecode").append("<br/><br/>");
		    $("#addmorecode").append("<input type='button' id='submit1' name='submit1' value='APPROVE SELLER'>");
		  });
	});
});
</script>
<script>
$(document).ready(function(){
	$("#addmorecode").on("click","#submit1",function(){
		//$('#submit1').click(function(){
		alert('Approve Seller Clicked!!');
		var sellername = $('#sellername option:selected').val();
		$.post( "sellerlistapprove.htm", { seller: sellername })
		.done(function(serverdata) {
			var obj = jQuery.parseJSON(serverdata);
			$("#successmessage").text("Successfully approved seller:\t"+obj.name);
			$("#successmessage").append("<br/><br/>");
			$("#successmessage").append("<a href='validatesellers.htm'>Click here to refresh the details!!</a>");
		});
	});
});	
</script>

</head>
<body>
	<c:url value="http://localhost:8080/controller/logout.htm" var="myURL">
	</c:url>

	<form>
		<a style="float: right" href="${myURL}">LOGOUT</a>
	</form>
	<form id="sellerlist" method="post" action="sellerlist.htm">
		<select id="sellername" name="sellername">
			<c:forEach items="${requestScope.sellerlist}" var="item">
				<option value="${item}">${item.getName()}</option>
			</c:forEach>
		</select> <br /> <br />
		<div id="addmorecode">
			<input type="button" id="submit" name='submit' value="DETAILS">
		</div>
	</form>
	<br />
	<br />
	<br />
	<div>
		<p id="displaysellerdetails"></p>
	</div>
	<div>
		<p id="successmessage"></p>
	</div>
</body>
</html>