
function getProductUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/";
}


function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/operator/order";
}

function getOrderItemUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/operator/orderItem";
}

function getPdfUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl;
}

function addToggle(event){
	$('#add-order-modal').modal('toggle');
}


var order = [];

const orderPriceMap = new Map();
const orderQuantityMap = new Map();




function getOrderItem(event){

	event.preventDefault();

	
	var quantityInInventory = 0;

	var url = getProductUrl()+"operator/inventory";
	  $.ajax({
		 url: url,
		 type: 'GET',
		 success: function(data) {
			var $form = $("#add-order-form");
			var json = toJson($form);
			var orderItem = JSON.parse(json);

		  for(var i in data){  	
			var e = data[i];
			console.log("hello :",e.quantity);
			console.log("1  :",e.barcode);
			console.log("2 :",orderItem.barcode);
			  if(e.barcode == orderItem.barcode){
				if(e.mrp < orderItem.sellingPrice){
					msgErrorstring("Selling price cannot be greater than " + e.mrp);
				}
			 else{
				quantityInInventory = e.quantity;
				console.log("into the selling price error1");
				if(orderPriceMap.has(orderItem.barcode) && orderPriceMap.get(orderItem.barcode) != orderItem.sellingPrice){
					console.log("into the selling price error2");
					msgErrorstring("Selling Price can only be "+ orderPriceMap.get(orderItem.barcode));
					// msgError("Selling Price can only be " + String.toString(orderPriceMap.get(orderItem.barcode)));
				}
				else{
					
				if(orderQuantityMap.has(orderItem.barcode)){
			
					let x = parseInt(orderQuantityMap.get(orderItem.barcode));
					if(x + parseInt(orderItem.quantity) <= quantityInInventory){
					orderQuantityMap.set(orderItem.barcode, x + parseInt(orderItem.quantity) + "");
					
					order = [];
			
					for (let [key, value] of orderPriceMap) {
						var orderItem = {"barcode":key,"sellingPrice":value,"quantity":orderQuantityMap.get(key)};
						console.log(orderItem);
						order.push(orderItem);
					}
				}
				else{
					console.log("adfs:" + quantityInInventory );
					msgErrorstring("Available quantity: "+ quantityInInventory);
				}
			
				}
				else{
			
					if(orderItem.quantity <= quantityInInventory){
			
					orderPriceMap.set(orderItem.barcode,orderItem.sellingPrice);
					orderQuantityMap.set(orderItem.barcode,orderItem.quantity);
			
					order.push(orderItem);
					}
					else{
						console.log("adfs:" + quantityInInventory );
						msgErrorstring("Available quantity: "+quantityInInventory);
					}
					
				}
				displayOrderItemList(order);
				}
			}
			  }
		  }
		 },
		 error: handleAjaxError
	  });
	
	console.log("quantity :: ", quantityInInventory);
	console.log("ended"); 
	
}

function addOrder(event){

	console.log(JSON.stringify(order));

	console.log("into the add order function ");
	var url = getOrderUrl();
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: JSON.stringify(order),
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
		    $("#add-order-modal").modal("toggle");
			msgSuccess("Order added Successfully");
	   		getOrderList();  
	   },
	   error: handleAjaxError
	});
	cancelOrder();
	return false;
}
function resetAddOrderDialog(){
	var $form = $("#order-add-form");
	$("#add-order-table tbody tr:not(:first-child)").remove();
	$form.trigger("reset");
}

function getOrderList(){
	var url = getOrderUrl();

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
		msgSuccess("Order deleted Successfully");
	   	getOrderList();  
	   },
	   error: handleAjaxError
	});
}



function displayOrderList(data){
	console.log("into the display order ");
	var $tbody = $('#order-table').find('tbody');
	$tbody.empty();
	for(i = data.length-1 ; i>=0 ; i--){
		var e = data[i];
		var status;
		var buttonHtml;
		if(e.invoiceGenerated){
			status = "Invoiced";
			buttonHtml = ' <button class="fa fa-file-text" id="invoice" data-toggle="tooltip" data-html="true" title="download invoice" style="border-radius :5px;border-color:grey"  onclick="downloadInvoice(' + e.id + ')"></button>'	
			buttonHtml += ' <button class="fa fa-eye" id="edit" id="view-order"  data-toggle="tooltip" data-html="true" title="view order" style="border-radius :5px;border-color:grey"  onclick="getOrderItemList(' + e.id + ')"></button>'
		}
		else{
			status = "Ordered";
			buttonHtml = ' <button class="fa fa-file-text" id="invoice" data-toggle="tooltip" data-html="true" title="download invoice" style="border-radius :5px;border-color:grey"  onclick="downloadInvoice(' + e.id + ')"></button>'	
			buttonHtml += ' <button class="fa fa-pencil" id="edit"  data-toggle="tooltip" data-html="true" title="view/edit order" style="border-radius :5px;border-color:grey"  onclick="displayEditOrder(' + e.id + ')"></button>'
		}
		// var buttonHtml = '<button class="fa fa-trash" id="delete"  data-toggle="tooltip" data-html="true" title="delete order" style="border-radius :5px;border-color:grey" onclick="deleteOrder(' + e.id + ')"></button>'
			var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.orderAddDate + '</td>'
		+ '<td>' + e.orderUpdatedDate + '</td>'
		+ '<td>' + status + '</td>'
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

function downloadInvoice(id){
	var url = getOrderUrl() + "/invoice/" + id;
	window.location.href = url;
	refresh();
}

function refresh(){
	getOrderList();
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
		msgError("Order cannot be empty");
	}
}


function processDropDown() {
    var url = getProductUrl()+"operator/product";
    var productData = null;
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            fillDropDown(data);
        },
        error: handleAjaxError
    });
}


function fillDropDown(data) {
	console.log("into the fill dropdown function");
    let barcodeArr = []
    data.forEach((e) => {
        barcodeArr.push(e.barcode);
    });

    barcodeArr = [...new Set(barcodeArr)];
    
    // BRAND
    var $barcodeDropdown = $('#barcode-dropdown-menu');
    $barcodeDropdown.empty();
    var $editBarcodeDropdown = $('#edit-barcode-dropdown-menu');
    $editBarcodeDropdown.empty();

    var firstRowBarcode = '<option value="none" selected disabled>Select Barcode</option>';
    $barcodeDropdown.append(firstRowBarcode);

    barcodeArr.forEach((barcode) => {
        var row = '<option value="' + barcode + '">' + barcode + '</option>';
        $barcodeDropdown.append(row);
        $editBarcodeDropdown.append(row);
    })
}

function displayOrderItemList(data){
	var $tbody = $('#order-item-table').find('tbody');
	// console.log("into the display function");
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button class="fa fa-trash" style="border:3px;border-color:black"  data-toggle="tooltip" data-html="true" title="remove Item from Cart" aria-hidden="true"  onclick="deleteOrderItem(`' +  e.barcode  + '`)"></button>'
		//  var buttonHtml = ' <button class="fa fa-pencil" style="border:8px;border-color:black" aria-hidden="true" onclick="displayEditOrderItem(' + e.barcode + ')"></button>'
		var row = '<tr id="' + e.barcode + '">'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>'  + e.sellingPrice + '</td>'
		+ '<td>'  + e.quantity + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}



function viewOrder(data){
	var $tbody = $('#view-order-item-table').find('tbody');
	console.log("into the display function");
	$tbody.empty();
	var sno = 1;
	for(var i in data){
		var e = data[i];
		var row = '<tr>'
		+ '<td>' + sno + '</td>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>'  + e.quantity + '</td>'
		+ '<td>'  + e.sellingPrice + '</td>'
		+ '</tr>';
        $tbody.append(row);
		sno += 1;
	}
}



function getOrderItemList(id) {
	console.log("into the function getORderList");
	$('#view-order-modal').modal('toggle');
    var url = document.location.href;
    var params = url.split('/');
    var order_id = params.at(-1);
	console.log("getorderurl : ",getOrderUrl());
	console.log("order_id : ",id);
    var url = getOrderUrl() + "/" + id;
	console.log("url is :: ",url);
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            console.log(data);
            // getPdf(data);
            viewOrder(data);
        },
        error: handleAjaxError
    });
}
// function displayEditOrderItem(barcode){

// }

function deleteOrderItem(barcode){
	console.log(barcode);
	// $("#order-item-table").closest('tr').remove();
	$("#"+barcode).remove();
	const index = order.indexOf(barcode);
	order.splice(index,1); 
	orderPriceMap.delete(barcode);
	orderQuantityMap.delete(barcode);
	 displayOrderItemList(order);

}

function displayOrder(data){
	$("#edit-barcode-dropdown-menu").val(data.barcode);
	$("#order-edit-form input[name=id]").val(data.id);	
	$("#order-edit-form input[name=orderAddDate]").val(data.orderAddDate);
	$("#order-edit-form input[name=orderUpdatedDate]").val(data.orderUpdatedDate);
	$("#order-edit-form input[name=invoiceGenerated]").val(data.invoiceGenerated);
	$('#edit-order-modal').modal('toggle');
}

function cancelOrder(){
	//$('#add-order-modal').modal('hide');
	document.getElementById("add-order-form").reset();
	order = [];
	$('#order-item-table').find('tbody').empty();
	orderPriceMap.clear();
	orderQuantityMap.clear();
	console.log( "order array size is :: " +  order.length);
}

//INITIALIZATION CODE
function init(){
	$('#add-order').click(addOrder);
	$('#add-order-button').click(addToggle);
	$('#cancel-order-button').click(cancelOrder);
	$('#cancel-order').click(cancelOrder);
    $('#add-new-row').click(addRow);
	$('#refresh-data').click(getOrderList);
	$('#add-order-form').submit(getOrderItem);
	$("#barcode-dropdown").change(function () {
        var selected = $(this).children("option:selected").val();
        alert("You have selected - " + selected);
    });
}

$(document).ready(init);
$(document).ready(getOrderList);
$(document).ready(processDropDown);

