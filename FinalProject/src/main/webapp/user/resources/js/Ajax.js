/**
 * jQuery Javascript Library - http://jquery.com/download/
 */
$(document).ready(function(){
	$('#submit').click(function(){	
		alert('INSIDE AJAX.JS!!!');
		var sellername = $('#sellername option:selected').val();
		alert(sellername);
		$.post( "sellerlist.htm", { seller: sellername })
		  .done(function(serverdata) {
		    var obj = jQuery.parseJSON(serverdata);
		    $("#displaysellerdetails").text(obj.name);
		    $("#addmorecode").append("<br/><br/>");
		    $("#addmorecode").append("<input type='button' id='submit1' name='submit1' value='APPROVE SELLER'>");
		  });
	});
});