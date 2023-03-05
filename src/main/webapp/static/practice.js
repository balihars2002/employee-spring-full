
var fileData = [];
var errorData = [];
var processCount = 0;
var uploadlength ,error=0 ;

function processData(){
	var file = $('#brandFile')[0].files[0];
	readFileData(file, readFileDataCallback);
	
}

function readFileDataCallback(results){
	fileData = results.data;
	if(fileData.length > 5000) {
		msgErrorstring("Max Upload Limit: 5000");
		return;
	}
	console.log("errordata.length ::",errorData.length);
	
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
			msgErrorstring('Errors : ' + error.toString());		
			 if(response.length == 0){
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

function resetUploadDialog(){

	var $file = $('#brandFile');
	$file.val('');
	$('#brandFileName').html("Choose File");

	processCount = 0;
	fileData = [];
	errorData = [];

	updateUploadDialog();
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

