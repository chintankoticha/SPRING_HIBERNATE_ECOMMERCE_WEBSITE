/**
 * 
 */	
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
             html +=	'<tr><td><input path="name" id="products' + index + '.name" name="products[' + index + '].name" size="10" required="required"/><font color="red"><form:errors path="name"/></font></td>';
             html+= '<td><input path="description" id="products' + index + '.description " name="products[' + index + '].description" size="10" required="required"/><font color="red"><form:errors path="description"/></font></td>';
             html+= '<td><input path="price" id="products' + index + '.price" name="products[' + index + '].price" size="10" required="required"/><font color="red"><form:errors path="price"/></font></td>';
             html+= '<td><input path="quantity" id="products' + index + '.quantity" name="products[' + index + '].quantity" size="10" required="required"/><font color="red"><form:errors path="quantity"/></font></td>';
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
	 
