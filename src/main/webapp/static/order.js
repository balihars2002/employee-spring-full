

function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order";
}


function addToggle(event){
	$('#add-order-modal').modal('toggle');
}

// BUTTON ACTION
function addOrderHelp(form,col){
	var serialisedArray = form.serializeArray();
	var len = serialisedArray.length;
	var orderItemFormList = [];
	for(i=0;i<len;i+=3){
		var orderItem = {};
		for(var propIdx = 0; propIdx < col; propIdx++){
			orderItem[serialisedArray[i + propIdx].name] = serialisedArray[i + propIdx].value;
		}
		orderItemFormList.push(orderItem);
	}

	var orderItemsObj = {orderItemFormList: orderItemFormList}
	return orderItemsObj;
}
function addOrder(event){

	console.log("into the add order function ");
	var url = getOrderUrl();
	//Set the values to update
	var $form = $("#order-form");
	var help = addOrderHelp($form,3);
	var json = JSON.stringify(help);
	console.log("Json : ",json);
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		getOrderList();  
			$("#add-order-modal").modal("toggle");
			//reset
	   },
	   error: handleAjaxError
	});

	return false;
}
function resetAddOrderDialog(){
	var $form = $("#order-add-form");
	$("#add-order-table tbody tr:not(:first-child)").remove();
	$form.trigger("reset");
}

function getOrderList(){
	var url = getOrderUrl();
	
	console.log(" into the get inventory function ");
	console.log(" the url int the get inv is :", url);
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrderList(data);  
	   },
	   error: handleAjaxError
	});
}

function deleteOrder(id){
	var url = getOrderUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getOrderList();  
	   },
	   error: handleAjaxError
	});
}



function displayOrderList(data){
	console.log("into the display order ");
	var $tbody = $('#order-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button class="fa fa-trash" title="delete order" style="border:0px;" onclick="deleteOrder(' + e.id + ')"></button>'
		 buttonHtml += ' <button class="fa fa-pencil" title="edit order" style="border:0px;"  onclick="displayEditOrder(' + e.id + ')"></button>'
		 buttonHtml += ' <button class="fa fa-file-text" title="download invoice" style="border:0px;"  onclick="displayEditOrder(' + e.id + ')"></button>'
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.date + '</td>'
		+ '<td>' + e.updatedDate + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}
function displayEditOrder(id) {
    $(location).prop('href', '/employee/ui/orderItem/' + id)
}
function viewOrder(){
		var url = getOrderUrl() + "/" + id;
		console.log(" into the view order function ");
	
		$.ajax({
			url: url,
			type: 'GET',
			success: function(data) {
					displayOrder(data);   
			},
			error: handleAjaxError
		 });	
	
}

function displayOrder(data){
	$("#order-edit-form input[name=id]").val(data.id);	
	// $("#order-edit-form input[name=barcode]").val(data.barcode);	
	// $("#order-edit-form input[name=brand]").val(data.brand);	
	// $("#order-edit-form input[name=category]").val(data.category);	
	// $("#order-edit-form input[name=productId]").val(data.productId);
	// $("#order-edit-form input[name=orderId]").val(data.orderId);	
	$("#order-edit-form input[name=date]").val(data.date);
	$("#order-edit-form input[name=updatedDate]").val(data.updatedDate);
	// $("#order-edit-form input[name=localDateTime]").val(data.localDateTime);
	$('#edit-order-modal').modal('toggle');
}

function addRow(){
	console.log("the add row function called");
	$("#add-order-row").clone().insertAfter("tr.add-order-row:last");
	console.log("new row has been added");
    $('.delete-new-row').click(deleteRow);

}

function deleteRow(){
	console.log("into the delete row");
	var count = $('#add-order-table tr').length;
	console.log(" row count :: ", count);
	if( count > 1 ){
		$("#add-order-table").on('click','.delete-new-row',function(){
			
			$(this).closest('tr').remove();
			
		});
	}
	else{
		alert("Order cannot be empty");
		throw new Error("cannot be empty");	
	}
	 console.log("exiting the delete row");
}

//INITIALIZATION CODE
function init(){
	$('#add-order').click(addOrder);
	$('#add-order-button').click(addToggle);
    $('#add-new-row').click(addRow);
    // $('.delete-new-row').click(deleteRow);
	// $('#btnDelete').click(deleteRow);
	// $('#update-order').click(updateOrder);
	 $('#refresh-data').click(getOrderList);
	// $('#upload-data').click(displayUploadData);
	// $('#process-data').click(processData);
	// $('#download-errors').click(downloadErrors);
    // $('#orderFile').on('change', updateFileName)
}

$(document).ready(init);
$(document).ready(getOrderList);
