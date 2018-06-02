/**
 * 
 */
$(document).ready(function() {
	$('#list').click(function(event){
		event.preventDefault();
		$('#products .item').addClass('list-group-item');
	});

	$('#grid').click(function(event){
		event.preventDefault();
		$('#products .item').removeClass('list-group-item');
		$('#products .item').addClass('grid-group-item');
	});

	$(".btn").click(function() {
		alert(this.id);
		var productId = this.id;
		$.post( "addToCart.htm", { productid: productId })
		  .done(function(serverdata) {
		    alert('PRODUCT ADDED TO CART!!');
		  });
	});
});