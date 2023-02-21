
function getProductUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/";
}

function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order";
}

function getOrderItemUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/orderItem";
}

function getPdfUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl;
}

function addToggle(event){
	$('#add-order-modal').modal('toggle');
}

var order= [];


function getOrderItem(event){

	event.preventDefault();

	var $form = $("#add-order-form");
  	var json = toJson($form);
  	var orderItem = JSON.parse(json);

  	console.log(orderItem);

  	order.push(orderItem);
	
	displayOrderItemList(order);

	// var url = getOrderUrl();

	// $.ajax({
	// 	url: url,
	// 	type: 'POST',
	// 	success: function(data) {
	// 			displayOrder(data);   
	// 	},
	// 	error: handleAjaxError
	//  });

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

		Toastify({
			text: "Order added Successfully",
			style: {
				background: "linear-gradient(to right,  #5cb85c, #5cb85c)",
			  },
			duration: 2500
			}).showToast();
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
		Toastify({
			text: "Order deleted Successfully",
			style: {
				background: "linear-gradient(to right,  #5cb85c, #5cb85c)",
			  },
			duration: 2500
			}).showToast();
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
		var status;
		if(e.invoiceGenerated){
			status = "Invoiced";
		}
		else{
			status = "Ordered";
		}
		var buttonHtml = '<button class="fa fa-trash" id="delete"  data-toggle="tooltip" data-html="true" title="delete order" style="border-radius :5px;border-color:grey" onclick="deleteOrder(' + e.id + ')"></button>'
		 buttonHtml += ' <button class="fa fa-pencil" id="edit"  data-toggle="tooltip" data-html="true" title="edit order" style="border-radius :5px;border-color:grey"  onclick="displayEditOrder(' + e.id + ')"></button>'
		 buttonHtml += ' <button class="fa fa-file-text" id="invoice" data-toggle="tooltip" data-html="true" title="download invoice" style="border-radius :5px;border-color:grey"  onclick="downloadInvoice(' + e.id + ')"></button>'
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.date + '</td>'
		+ '<td>' + e.updatedDate + '</td>'
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
		var buttonHtml = '<button class="fa fa-trash" style="border:3px;border-color:black" aria-hidden="true"  onclick="deleteOrderItem(' + e.id + ')"></button>'
		var buttonHtml = ' <button class="fa fa-pencil" style="border:8px;border-color:black" aria-hidden="true" onclick="displayEditOrderItem(' + e.id + ')"></button>'
		var row = '<tr>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>'  + e.mrp + '</td>'
		+ '<td>'  + e.quantity + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}


function displayOrder(data){
	$("#edit-barcode-dropdown-menu").val(data.barcode);
	$("#order-edit-form input[name=id]").val(data.id);	
	$("#order-edit-form input[name=date]").val(data.date);
	$("#order-edit-form input[name=updatedDate]").val(data.updatedDate);
	$("#order-edit-form input[name=invoiceGenerated]").val(data.invoiceGenerated);
	$('#edit-order-modal').modal('toggle');
}


//INITIALIZATION CODE
function init(){
	$('#add-order').click(addOrder);
	$('#add-order-button').click(addToggle);
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

