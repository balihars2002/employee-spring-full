
function getBrandReportUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/brandReport";
}

function getBrandList(){
	var url = getBrandReportUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayBrandList(data);  
	   },
	   error: handleAjaxError
	});
}

function addBrand(event){
	//Set the values to update
	console.log("JSON :::");
	var $form = $("#brand-report-form");
	var json = toJson($form);
	var url = getBrandReportUrl();
    console.log("JSON :::",json);
    console.log("url is  ",url);
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
        console.log(response);
        console.log("One called : ", response);
        displayBrandList(response);  
	   },
	   error: handleAjaxError
	});

	return false;
}


function displayBrandList(data){
	var $tbody = $('#brandReport-table').find('tbody');
	$tbody.empty();
	var sno = 1;
	for(var i in data){
		var e = data[i];
		var row = '<tr>'
		+ '<td>' + sno + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>'  + e.category + '</td>'
		+ '</tr>';
        $tbody.append(row);
		sno += 1;
	}
}

function displayBrand(data){
	$("#brand-edit-form input[name=brand]").val(data.brand);	
	$("#brand-edit-form input[name=category]").val(data.category);	
	$('#edit-brand-modal').modal('toggle');
}

function downloadCsvFile(data){
	var url = getBrandReportUrl() + "/exportCsv";
	window.location.href = url;
}
//INITIALIZATION CODE
function init(){
    $('#get-brand-report').click(addBrand);
	$('#download-csv').click(downloadCsvFile);
}

$(document).ready(init);
$(document).ready(addBrand);

