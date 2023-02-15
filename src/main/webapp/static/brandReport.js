
function getBrandUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/brandReport";
}

function getBrandList(){
	var url = getBrandUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayBrandList(data);  
	   },
	   error: handleAjaxError
	});
}

function displayBrandList(data){
	var $tbody = $('#brandReport-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>'  + e.category + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}
function downloadCsvFile(data){
	var url = getBrandUrl() + "/exportCsv";
	window.location.href = url;
}
//INITIALIZATION CODE
function init(){
	$('#download-csv').click(downloadCsvFile);
}

$(document).ready(init);
$(document).ready(getBrandList);

