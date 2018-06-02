/**
 * 
 */
$(document).ready(function(){
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
});