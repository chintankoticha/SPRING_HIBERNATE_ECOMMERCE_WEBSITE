/**
 * 
 */
$(document).ready(function(){
	$(document).on('click', '.update', function(e) {
		e.preventDefault();	
		//alert($(this).data('update'));
		var pid = $(this).data('update');
		var a= jQuery($(this).parent().parent()).find('td:eq(4)').find('input').val();
		//alert(a);
		$.post( "updatecart.htm", { pid: pid ,quantity:a})
		.done(function(serverdata) {
			//alert('Deleted from Database!!');
			alert('Back from db!!');
			var price = serverdata;
			$('input[name=totalprice]').val('$'+price);
		});
	});
	
	$(document).on('click', '.delete', function(e) {
		e.preventDefault();	
		var pid = $(this).data('delete');
		var row =  $("#"+pid).fadeOut(3000);
		$.post( "deletecart.htm", { pid: pid})
		
		.done(function(serverdata) {
			//alert('Deleted from Database!!');
			alert('Back from db!!');
			var price = serverdata;
			$('input[name=totalprice]').val('$'+price);
		});
	});
});