

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
// function updateOrder(event){
// 	$('#edit-order-modal').modal('toggle');
// 	//Get the ID
// 	var id = $("#order-edit-form input[name=id]").val();	
// 	var url = getOrderUrl() + "/" + id;

// 	//Set the values to update
// 	var $form = $("#order-edit-form");
// 	var json = toJson($form);

// 	$.ajax({
// 	   url: url,
// 	   type: 'PUT',
// 	   data: json,
// 	   headers: {
//        	'Content-Type': 'application/json'
//        },	   
// 	   success: function(response) {
// 	   		getOrderList();   
// 	   },
// 	   error: handleAjaxError
// 	});

// 	return false;
// }


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

// function deleteOrder(id){
// 	var url = getOrderUrl() + "/" + id;

// 	$.ajax({
// 	   url: url,
// 	   type: 'DELETE',
// 	   success: function(data) {
// 	   		getOrderList();  
// 	   },
// 	   error: handleAjaxError
// 	});
// }

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


// function processData(){
// 	var file = $('#orderFile')[0].files[0];
// 	readFileData(file, readFileDataCallback);
// }

// function readFileDataCallback(results){
// 	fileData = results.data;
// 	uploadRows();
// }

// function uploadRows(){
// 	//Update progress
// 	updateUploadDialog();
// 	//If everything processed then return
// 	if(processCount==fileData.length){
// 		return;
// 	}
	
// 	//Process next row
// 	var row = fileData[processCount];
// 	processCount++;
	
// 	var json = JSON.stringify(row);
// 	var url = getOrderUrl();

// 	//Make ajax call
// 	$.ajax({
// 	   url: url,
// 	   type: 'POST',
// 	   data: json,
// 	   headers: {
//        	'Content-Type': 'application/json'
//        },	   
// 	   success: function(response) {
// 	   		uploadRows();  
// 	   },
// 	   error: function(response){
// 	   		row.error=response.responseText
// 	   		errorData.push(row);
// 	   		uploadRows();
// 	   }
// 	});

// }

// function downloadErrors(){
// 	writeFileData(errorData);
// }

//UI DISPLAY METHODS

function displayOrderList(data){
	console.log("into the display order ");
	var $tbody = $('#order-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button class="fa fa-trash" style="border:0px;" onclick="deleteOrder(' + e.id + ')"></button>'
		 buttonHtml += ' <button class="fa fa-pencil" style="border:0px;"  onclick="displayEditOrder(' + e.id + ')"></button>'
		 buttonHtml += ' <button class="fa fa-eye" style="border:0px;" onclick="viewOrder(' + e.id + ')"></button>'
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.localDateTime + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

function viewOrder(id){
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
function displayEditOrder(id){
	var url = getOrderUrl() + "/" + id;
	console.log(" into the get edit order function ");
	console.log(" the url int the get inv is :", url);
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrder(data);   
	   },
	   error: handleAjaxError
	});	
}

// function resetUploadDialog(){
// 	//Reset file name
// 	var $file = $('#orderFile');
// 	$file.val('');
// 	$('#orderFileName').html("Choose File");
// 	//Reset various counts
// 	processCount = 0;
// 	fileData = [];
// 	errorData = [];
// 	//Update counts	
// 	updateUploadDialog();
// }

// function updateUploadDialog(){
// 	$('#rowCount').html("" + fileData.length);
// 	$('#processCount').html("" + processCount);
// 	$('#errorCount').html("" + errorData.length);
// }

// function updateFileName(){
// 	var $file = $('#orderFile');
// 	var fileName = $file.val();
// 	$('#orderFileName').html(fileName);
// }

// function displayUploadData(){
//  	resetUploadDialog(); 	
// 	$('#upload-order-modal').modal('toggle');
// }

function displayInventory(data){
	$("#order-edit-form input[name=id]").val(data.id);	
	// $("#order-edit-form input[name=barcode]").val(data.barcode);	
	// $("#order-edit-form input[name=brand]").val(data.brand);	
	// $("#order-edit-form input[name=category]").val(data.category);	
	// $("#order-edit-form input[name=productId]").val(data.productId);
	// $("#order-edit-form input[name=orderId]").val(data.orderId);	
	$("#order-edit-form input[name=localDateTime]").val(data.localDateTime);
	$('#edit-order-modal').modal('toggle');
}

function addRow(){
	console.log("the add row function called");
	$("#add-order-row").clone().insertAfter("tr.add-order-row:last");
	console.log("new row has been added");
}
// function deleteRow(){
// 	console.log("into the delete row function");
// 	$("#add-order-row").closest("add-order-row").remove();
//     e.preventDefault();
// 	console.log("exiting the delete row fucntion");
// }
// function deleteRow(){
// 	console.log("into the delete row");
// 	$("#add-order-table").on('click','.btnDelete',function(){
// 		$(this).closest('tr').remove();
// 	 });
// 	 console.log("exiting the delete row");
// }
// function addRow() {
//     const node = document.getElementById("myList2").lastChild;
//     const clone = node.cloneNode(true);
//     document.getElementById("myList1").appendChild(clone);
//   }
//INITIALIZATION CODE
function init(){
	$('#add-order').click(addOrder);
	$('#add-order-button').click(addToggle);
    $('#add-new-row').click(addRow);
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
