

function getOrderItemUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/orderItem";
}

function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order";
}

//BUTTON ACTION
function addOrderItem(event){
	//Set the values to update
	var $form = $("#orderItem-form");
	var json = toJson($form);
	var url = getOrderItemUrl();

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		getOrderItemList();  
	   },
	   error: handleAjaxError
	});

	return false;
}

function updateOrderItem(event){
	$('#edit-orderItem-modal').modal('toggle');
	//Get the ID
	var id = $("#orderItem-edit-form input[name=id]").val();	
	var url = getOrderItemUrl() + "/" + id;
	console.log(" url is :: ",url);
	//Set the values to update
	var $form = $("#orderItem-edit-form");
	var json = toJson($form);
	console.log(" JSON :: ",json);
	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		getOrderItemList();   
	   },
	   error: handleAjaxError
	});

	return false;
}


function getOrderItemList(id){
	var url = getOrderItemUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrderItemList(data);  
	   },
	   error: handleAjaxError
	});
}

function deleteOrderItem(id){
	var url = getOrderItemUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getOrderItemList();  
	   },
	   error: handleAjaxError
	});
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#orderItemFile')[0].files[0];
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
	fileData = results.data;
	uploadRows();
}

function uploadRows(){
	//Update progress
	updateUploadDialog();
	//If everything processed then return
	if(processCount==fileData.length){
		return;
	}
	
	//Process next row
	var row = fileData[processCount];
	processCount++;
	
	var json = JSON.stringify(row);
	var url = getOrderItemUrl();

	//Make ajax call
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		uploadRows();  
	   },
	   error: function(response){
	   		row.error=response.responseText
	   		errorData.push(row);
	   		uploadRows();
	   }
	});
    
}

function downloadErrors(){
	writeFileData(errorData);
}

//UI DISPLAY METHODS

function displayOrderItemList(data){
	var $tbody = $('#order-item-table').find('tbody');
	console.log("into the display function");
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button class="fa fa-trash" style="border:3px;border-color:black" aria-hidden="true"  onclick="deleteOrderItem(' + e.id + ')"></button>'
		var buttonHtml = ' <button class="fa fa-pencil" style="border:8px;border-color:black" aria-hidden="true" onclick="displayEditOrderItem(' + e.id + ')"></button>'
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>'  + e.quantity + '</td>'
		+ '<td>'  + e.sellingPrice + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}
// function viewOrder(){

// 	var url = getOrderUrl() + "/" + id;
// 	console.log(" into the view order function ");

// 	$.ajax({
// 		url: url,
// 		type: 'GET',
// 		success: function(data) {
// 				displayOrder(data);   
// 		},
// 		error: handleAjaxError
// 	 });	

// }
// returns the orderitems by order id;
// var order_id;
function getOrderList() {
	console.log("into the function ");
    var url = document.location.href;
    var params = url.split('/');
    var order_id = params.at(-1);
    var url = getOrderUrl() + "/" + order_id;
	console.log("url is :: ",url);
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            console.log(data);
            // getPdf(data);
            displayOrderItemList(data);
        },
        error: handleAjaxError
    });
}
function displayEditOrderItem(id){
	var url = getOrderItemUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrderItem(data);   
	   },
	   error: handleAjaxError
	});	
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#orderItemFile');
	$file.val('');
	$('#orderItemFileName').html("Choose File");
	//Reset various counts
	processCount = 0;
	fileData = [];
	errorData = [];
	//Update counts	
	updateUploadDialog();
}

function updateUploadDialog(){
	$('#rowCount').html("" + fileData.length);
	$('#processCount').html("" + processCount);
	$('#errorCount').html("" + errorData.length);
}

function updateFileName(){
	var $file = $('#orderItemFile');
	var fileName = $file.val();
	$('#orderItemFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog(); 	
	$('#upload-orderItem-modal').modal('toggle');
}

function displayOrderItem(data){
    $("#orderItem-edit-form input[name=id]").val(data.id);	
	$("#orderItem-edit-form input[name=barcode]").val(data.barcode);	
	$("#orderItem-edit-form input[name=quantity]").val(data.quantity);	
	$("#orderItem-edit-form input[name=sellingPrice]").val(data.sellingPrice);	
	$('#edit-orderItem-modal').modal('toggle');
}


//INITIALIZATION CODE
function init(){
	 $('#add-orderItem').click(addOrderItem);
	 $('#update-the-orderItem').click(updateOrderItem);
	//  $('#refresh-data').click(getOrderItemList);
	// $('#upload-data').click(displayUploadData);
	// $('#process-data').click(processData);
	// $('#download-errors').click(downloadErrors);
    // $('#orderItemFile').on('change', updateFileName)
}

$(document).ready(init);
$(document).ready(getOrderList);

