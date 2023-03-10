
function getRole(){
	var role = $("meta[name=userRole]").attr("content")
	console.log(" role :: ",role);
	return role;
}
function getBrandUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/";
}
var brands = [];
function addBrand(event){
	var $form = $("#add-brand-form");
	var json = toJson($form);
	var url = getBrandUrl()+"brand";
	console.log("KSON :: ",json);
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
			$('#add-brand-modal').modal('toggle');
			$form.trigger("reset");
			msgSuccess("Brand added Successfully");
	   		getBrandList();  
	   },
	   error: function(response){
		msgError(response.responseText);
		handleAjaxError
	   }
	});

	return false;
}

function updateBrand(event){
	
	var id = $("#brand-edit-form input[name=id]").val();	
	var url = getBrandUrl() + "brand/" + id;
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
			$('#edit-brand-modal').modal('toggle');
			msgSuccess("Brand updated successfully");
	   		getBrandList();   
	   },
	   error: function(response){
		msgError(response.responseText);
		handleAjaxError
	   }
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
			background: "linear-gradient(to right,  #5cb85c,  #5cb85c)",
		  },
		duration: 2500
		}).showToast();
	   		getBrandList();  
	   },
	   error: handleAjaxError
	});
}

var fileData = [];
var errorData = [];
var processCount = 0;
var uploadlength ,error=0 ;

function processData(){
	
	
	var file = $('#brandFile')[0].files[0];
	readFileData(file, readFileDataCallback);

	console.log(file);
    if(!file){
        msgErrorstring("Please select a file");
        return;
    }
	
}

function readFileDataCallback(results){
	fileData = results.data;
	if(fileData.length > 5000) {
		msgErrorstring("Max Upload Limit: 5000");
		return;
	}
	console.log("errordata.length ::",errorData.length);
	// if(errorData.length){
	// document.getElementById("download-errors").disabled = false;
	// }

	uploadRows(); 
}

function uploadFileHelper(data){
	// console.log("second");
	console.log("data.length :: " ,data.length);
	uploadlength = data.length;
	json = JSON.stringify(data); 
	var url = getBrandUrl() + "brandTsv";
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
		 error =  response.length;
			uploadlength -= response.length;
			 errorData = JSON.stringify(response);
			 msgSuccess('Brands Uploaded:  ' + uploadlength.toString());	
			// msgErrorstring('Errors : ' + error.toString());		
			 if(response.length == 0){
				$('#upload-brand-modal').trigger("reset");
				$('#upload-brand-modal').modal('toggle');
			 }
			 else{
				document.getElementById("download-errors").disabled = false;
			 }
			 updateUploadDialog();
			 getBrandList();  
	   },
	   error: function(response){
			msgErrorstring("Error");
	   },
	      
	});
}

var array = [];
function uploadRows(){
	
	console.log("first");
	if(fileData.length == 0){
		msgErrorstring("No Data to upload");
		return;
	}
	
	while(1){
	if(processCount==fileData.length){
		break;
	}
	
	var row = fileData[processCount];
	processCount++;
	errorData.push(row);
	var json = JSON.stringify(row);
	console.log("json :: ",json);
	array.push(row);
	
	}
	console.log("consoling array::");
	console.log(array);

	uploadFileHelper(array);
}

function downloadErrors(){
	// console.log("array 0::", errorData[0]);
	writeFileData(errorData);
}


function displayBrandList(data){ 	
	
	var $tbody = $('#brand-table').find('tbody');
	$tbody.empty();
	var sNo = 1;
	for(i = data.length-1 ; i>=0 ; i--){
		var e = data[i];
		var buttonHtml = ' <button class="fa fa-pencil" id="edit-button"  data-toggle="tooltip" data-html="true" title="edit brand" style="border-radius :5px;border-color:grey" aria-hidden="true" onclick="displayEditBrand(' + e.id + ')"></button>'
		var row = '<tr>'
		+ '<td>' + sNo + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>'  + e.category + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
		sNo += 1;
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

	var $file = $('#brandFile');
	$file.val('');
	$('#brandFileName').html("Choose File");

	processCount = 0;
	fileData = [];
	errorData = [];

	updateUploadDialog();
}

function disableButtons(){
	
	var role = getRole();
	if(role == "operator"){
	document.getElementById("top-element").hidden = true;
	document.getElementById("break").hidden = true;
	
	}
}

function updateUploadDialog(){
	$('#rowCount').html("" + fileData.length);
	$('#processCount').html("" + processCount);
	$('#errorCount').html("" + error);
}

function updateFileName(){
	var $file = $('#brandFile');
	var fileName = $file.val();
	$('#brandFileName').html(fileName);
}

function displayUploadData(){
	 document.getElementById("download-errors").disabled = true;
 	resetUploadDialog(); 	
	$('#upload-brand-modal').modal('toggle');
}

function addToggle(event){
	$('#add-brand-modal').modal('toggle');
}

function cancelButton(){
	console.log("cancel function");
	$("#add-brand-form").trigger("reset");
}
function displayBrand(data){
	$("#brand-edit-form input[name=brand]").val(data.brand);	
	$("#brand-edit-form input[name=category]").val(data.category);	
	$("#brand-edit-form input[name=id]").val(data.id);	
	$('#edit-brand-modal').modal('toggle');
}

function init(){
	$('#cancel-top').click(cancelButton);
	$('#cancel-brand').click(cancelButton);
	$('#add-brand-button').click(addToggle);
	$('#add-brand').click(addBrand);
	$('#update-brand').click(updateBrand);
	$('#refresh-data').click(getBrandList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#brandFile').on('change', updateFileName)

}

$(document).ready(disableButtons);
$(document).ready(init);
$(document).ready(getBrandList);

