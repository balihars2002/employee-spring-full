
function getRole(){
	var role = $("meta[name=userRole]").attr("content")
	console.log(" role :: ",role);
	return role;
}
function getBrandUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/";
}
	
//BUTTON ACTION
function addBrand(event){
	//Set the values to update
	var $form = $("#brand-form");
	var json = toJson($form);
	var url = getBrandUrl()+"brand";

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
		Toastify({
			text: "Brand added Successfully",
			style: {
				background: "linear-gradient(to right,  #008000, #008000)",
			  },
			duration: 3500
			}).showToast();
	   		getBrandList();  
	   },
	   error: handleAjaxError
	});

	return false;
}

function updateBrand(event){
	$('#edit-brand-modal').modal('toggle');
	//Get the ID
	var id = $("#brand-edit-form input[name=id]").val();	
	var url = getBrandUrl() + "brand/" + id;

	//Set the values to update
	var $form = $("#brand-edit-form");
	var json = toJson($form);

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
		Toastify({
			text: "Brand updated Successfully",
			style: {
				background: "linear-gradient(to right,  #008000, #008000)",
			  },
			duration: 3500
			}).showToast();
	   		getBrandList();   
	   },
	   error: handleAjaxError
	});

	return false;
}


function getBrandList(){
	var url = getBrandUrl() + "operator/brand";
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayBrandList(data);  
	   },
	   error: handleAjaxError
	});
}

function deleteBrand(id){
	var url = getBrandUrl() + "/operator/brand" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
		Toastify({
		text: "Brand deleted Successfully",
		style: {
			background: "linear-gradient(to right,  #008000, #008000)",
		  },
		duration: 3500
		}).showToast();
	   		getBrandList();  
	   },
	   error: handleAjaxError
	});
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#brandFile')[0].files[0];
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
	var url = getBrandUrl();

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

function displayBrandList(data){ 	
	
	var $tbody = $('#brand-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		// var buttonHtml = '<button class="fa fa-trash" style="border:0px;" aria-hidden="true"  onclick="deleteBrand(' + e.id + ')"></button>'
		var buttonHtml = ' <button class="fa fa-pencil" id="edit-button" style="border:0.5px;border-color:grey" aria-hidden="true" onclick="displayEditBrand(' + e.id + ')"></button>'
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>'  + e.category + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
	if(getRole()=="operator"){
		deleteEditButton();
	}
}

function deleteEditButton(){
	var help = document.getElementById("brand-table");

		var rowcount = help.rows;
		console.log("the number of rows ::",rowcount);
		console.log(help);
		for(let i=0;i<rowcount.length ;i++){
			rowcount[i].deleteCell(3);
	}
}
function displayEditBrand(id){
	var url = getBrandUrl() + "operator/brand/" + id;
	console.log(" url is :",url);
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayBrand(data);   
	   },
	   error: handleAjaxError
	});	
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#brandFile');
	$file.val('');
	$('#brandFileName').html("Choose File");
	//Reset various counts
	processCount = 0;
	fileData = [];
	errorData = [];
	//Update counts	
	updateUploadDialog();
}

function disableButtons(){
	var role = getRole();
	if(role == "operator"){
	document.getElementById("brand-form").hidden = true;
	// document.getElementById("upload-data").disabled = true;
	}
	// document.getElementById("add-brand").disabled = true;
}

function updateUploadDialog(){
	$('#rowCount').html("" + fileData.length);
	$('#processCount').html("" + processCount);
	$('#errorCount').html("" + errorData.length);
}

function updateFileName(){
	var $file = $('#brandFile');
	var fileName = $file.val();
	$('#brandFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog(); 	
	$('#upload-brand-modal').modal('toggle');
}

function displayBrand(data){
	$("#brand-edit-form input[name=brand]").val(data.brand);	
	$("#brand-edit-form input[name=category]").val(data.category);	
	$("#brand-edit-form input[name=id]").val(data.id);	
	$('#edit-brand-modal').modal('toggle');
}

//INITIALIZATION CODE
function init(){
	$('#add-brand').click(addBrand);
	$('#update-brand').click(updateBrand);
	$('#refresh-data').click(getBrandList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#brandFile').on('change', updateFileName)

	
}

// $(document).ready(getRole);
$(document).ready(disableButtons);
$(document).ready(init);
$(document).ready(getBrandList);

