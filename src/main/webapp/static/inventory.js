
function getProductUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/";
}

function getInventoryUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/";
}
	
function getRole(){
	var role = $("meta[name=userRole]").attr("content")
	console.log(" role :: ",role);
	return role;
}

//BUTTON ACTION
function addInventory(event){
	//Set the values to update
	var $form = $("#add-inventory-form");
	var json = toJson($form);
	var url = getInventoryUrl()+"inventory";
	console.log("JSON : ",json);
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
		$('#add-inventory-modal').modal('toggle');
			msgSuccess("Product added to Inventory Successfully");
	   		getInventoryList();  
	   },
	   error: function(response){
		msgError(response.responseText);
		handleAjaxError
	   }
	});

	return false;
}

function updateInventory(event){
	event.preventDefault();
	
	//Get the ID
	var id = $("#inventory-edit-form input[name=id]").val();	
	var url = getInventoryUrl() + "inventory/" + id;

	//Set the values to update
	var $form = $("#inventory-edit-form");
	var json = toJson($form);
	console.log(" json ***** : ", json);
	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
		   $('#edit-inventory-modal').modal('toggle');
		   msgSuccess("Product quantity in Inventory updated Successfully");
	   	   getInventoryList();   
	   },
	   error: function(response){
	        msgError(response.responseText);
			handleAjaxError
	   }
	});

	return false;
}


function getInventoryList(){
	var url = getInventoryUrl()+"operator/inventory";
	
	console.log(" into the get inventory function ");
	console.log(" the url int the get inv is :", url);
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayInventoryList(data);  
	   },
	   error: handleAjaxError
	});
}

function deleteInventory(id){
	var url = getInventoryUrl() + "inventory/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
		msgSuccess("Product removed from Inventory Successfully");
	   	getInventoryList();  
	   },
	   error: function(response){
		msgError(response.responseText);
		handleAjaxError
	   }
	});
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#inventoryFile')[0].files[0];
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
	var url = getInventoryUrl()+"inventory";

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

function displayInventoryList(data){
	console.log("into the display inventory ");
	var $tbody = $('#inventory-table').find('tbody');
	$tbody.empty();
	var sno = 1;
	for(var i in data){
		var e = data[i];
		// var buttonHtml = '<button class="fa fa-trash"  data-toggle="tooltip" data-html="true" title="delete inventory" style="border-radius :5px;border-color:grey" onclick="deleteInventory(' + e.id + ')"></button>'
		var buttonHtml = ' <button class="fa fa-pencil"  data-toggle="tooltip" data-html="true" title="edit inventory" style="border-radius :5px;border-color:grey" onclick="displayEditInventory(' + e.id + ')"></button>'
		var row = '<tr>'
		+ '<td>' + sno + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>' + e.category + '</td>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>' + e.mrp + '</td>'
		+ '<td>' + e.name + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
		sno += 1;
	}
	if(getRole()=="operator"){
		deleteEditButton();
	}
}


function deleteEditButton(){
	var help = document.getElementById("inventory-table");

		var rowcount = help.rows;
		console.log("the number of rows ::",rowcount);
		console.log(help);
		for(let i=0;i<rowcount.length ;i++){
			rowcount[i].deleteCell(7);
	}
}

function displayEditInventory(id){
	var url = getInventoryUrl() + "operator/inventory/" + id;
	console.log(" into the get edit inventory function ");
	console.log(" the url int the get inv is :", url);
	//console.log("the inventory is :",data);
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayInventory(data);   
	   },
	   error: handleAjaxError
	});	
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

function disableButtons(){
	var role = getRole();
	if(role == "operator"){
	document.getElementById("inventory-form").hidden = true;
	// document.getElementById("upload-data").disabled = true;
	}
}


function resetUploadDialog(){
	//Reset file name
	var $file = $('#inventoryFile');
	$file.val('');
	$('#inventoryFileName').html("Choose File");
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
	var $file = $('#inventoryFile');
	var fileName = $file.val();
	$('#inventoryFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog(); 	
	$('#upload-inventory-modal').modal('toggle');
}

function addToggle(event){
	$('#add-inventory-modal').modal('toggle');
}

function cancelButton(){
	console.log("cancel function");
	$("#add-inventory-form").trigger("reset");
}

function displayInventory(data){
	$("#edit-barcode-dropdown-menu").val(data.barcode);
	$("#inventory-edit-form input[name=id]").val(data.id);
	$("#inventory-edit-form input[name=productId]").val(data.productId);	
	$("#inventory-edit-form input[name=brand]").val(data.brand);
	$("#inventory-edit-form input[name=category]").val(data.category);		
	$("#inventory-edit-form input[name=mrp]").val(data.mrp);
	$("#inventory-edit-form input[name=name]").val(data.name);
	$("#inventory-edit-form input[name=quantity]").val(data.quantity);
	$('#edit-inventory-modal').modal('toggle');
}


//INITIALIZATION CODE
function init(){
	$('#cancel-top').click(cancelButton);
	$('#cancel-inventory').click(cancelButton);
	$('#add-inventory-button').click(addToggle);
	$('#add-inventory').click(addInventory);
	$('#update-inventory').click(updateInventory);
	$('#refresh-data').click(getInventoryList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#inventoryFile').on('change', updateFileName)
	$("#barcode-dropdown").change(function () {
        var selected = $(this).children("option:selected").val();
        alert("You have selected - " + selected);
    });
}


$(document).ready(disableButtons);
$(document).ready(init);
$(document).ready(getInventoryList);
$(document).ready(processDropDown);

