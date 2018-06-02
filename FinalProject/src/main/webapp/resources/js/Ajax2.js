/**
 * jQuery Javascript Library - http://jquery.com/download/
 */
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