

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
	var $form = $("#inventory-form");
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
		
	   		getInventoryList();  
	   },
	   error: handleAjaxError
	});

	return false;
}

function updateInventory(event){
	$('#edit-inventory-modal').modal('toggle');
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
	   		getInventoryList();   
	   },
	   error: handleAjaxError
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
	   		getInventoryList();  
	   },
	   error: handleAjaxError
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
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button class="fa fa-trash" style="border:0.5px;border-color=grey" onclick="deleteInventory(' + e.id + ')"></button>'
		buttonHtml += ' <button class="fa fa-pencil" style="border:0.5px;border-color=grey" onclick="displayEditInventory(' + e.id + ')"></button>'
		var row = '<tr>'
		+ '<td>' + e.productId + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>' + e.category + '</td>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>' + e.mrp + '</td>'
		+ '<td>' + e.name + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
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

function displayInventory(data){
	$("#inventory-edit-form input[name=id]").val(data.id);
	$("#inventory-edit-form input[name=productId]").val(data.productId);	
	$("#inventory-edit-form input[name=brand]").val(data.brand);
	$("#inventory-edit-form input[name=category]").val(data.category);	
	$("#inventory-edit-form input[name=barcode]").val(data.barcode);	
	$("#inventory-edit-form input[name=mrp]").val(data.mrp);
	$("#inventory-edit-form input[name=name]").val(data.name);
	$("#inventory-edit-form input[name=quantity]").val(data.quantity);
	$('#edit-inventory-modal').modal('toggle');
}


//INITIALIZATION CODE
function init(){
	$('#add-inventory').click(addInventory);
	$('#update-inventory').click(updateInventory);
	$('#refresh-data').click(getInventoryList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#inventoryFile').on('change', updateFileName)
}


$(document).ready(disableButtons);
$(document).ready(init);
$(document).ready(getInventoryList);

