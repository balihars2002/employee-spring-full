
function getSalesReportUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/salesReport";
}
function getBrandUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/";
}
	
//BUTTON ACTION
function addBrand(event){
	//Set the values to update
	var $form = $("#sales-report-form");
	var json = toJson($form);
	var url = getSalesReportUrl();
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
        displaySalesReportList(response);  
	   },
	   error: handleAjaxError
	});

	return false;
}

var brandsAndCategory = {};
var brands = new Set();

function getBrandList() {
    var url = getBrandUrl() + "operator/brand";
    console.log(url);
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            console.log(data);
            brandOption(data);
            // editbrandOption(data);
        },
        error: handleAjaxError
    });
}
// function sortBrand(a, b) {
//     if (a.brand > b.brand) {
//         return 1;
//     }
//     else if (a.brand < b.brand) {
//         return -1;
//     }
//     return 0;
// }
function brandOption(data) {
    console.log("brand options function");
    // data.sort(sortBrand);
    let selectTag = $('#inputProductBrandName');
    console.log(selectTag)
    selectTag.empty();
    for (let i in data) {
        let e = data[i];
        brands.add(e.brand);
        if (brandsAndCategory[e.brand])
            brandsAndCategory[e.brand].add(e.category);
        else{
            brandsAndCategory[e.brand] = new Set([e.category]);
        }
    }
    let brandOption = '<option selected disabled value="">' + "Select Brand" + '</option>'
    selectTag.append(brandOption);
    for (brandName of brands.values()) {
        //console.log(brandName);
        let option = $('<option></option>').attr("value", brandName).text(brandName);
        selectTag.append(option);
    }
    categoryOption();
}
function categoryOption() {
    console.log("world");
    let brd = $('#inputProductBrandName')[0].value;
    let selectTag = $("#inputProductBrandCategoryName");
    selectTag.empty();
    let categoryOption = '<option selected disabled value> Select Category</option>';
    selectTag.append(categoryOption);
    if (brd.length != 0) {
        for (categoryName of brandsAndCategory[brd].values()) {
            console.log(categoryName);
            let option1 = $('<option></option>').attr("value", categoryName).text(categoryName);
            selectTag.append(option1);
        }
    }
}

function getOrderList(){
	var url = getSalesReportUrl();
	$.ajax({
	   url: url,
	   type: 'POST',
	   success: function(data) {
	   		displayBrandList(data);  
	   },
	   error: handleAjaxError
	});
}

// function deleteBrand(id){
// 	var url = getSalesReportUrl() + "/" + id;

// 	$.ajax({
// 	   url: url,
// 	   type: 'DELETE',
// 	   success: function(data) {
// 	   		getOrderList();  
// 	   },
// 	   error: handleAjaxError
// 	});
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
// 	var url = getBrandUrl();

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

function displaySalesReportList(data){
	var $tbody = $('#salesReport-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var row = '<tr>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>'  + e.category + '</td>'
		+ '<td>'  + e.quantity + '</td>'
		+ '<td>'  + e.revenue + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

// function displayEditBrand(id){
// 	var url = getBrandUrl() + "/" + id;
// 	$.ajax({
// 	   url: url,
// 	   type: 'GET',
// 	   success: function(data) {
// 	   		displayBrand(data);   
// 	   },
// 	   error: handleAjaxError
// 	});	
// }

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

// function updateUploadDialog(){
// 	$('#rowCount').html("" + fileData.length);
// 	$('#processCount').html("" + processCount);
// 	$('#errorCount').html("" + errorData.length);
// }

// function updateFileName(){
// 	var $file = $('#brandFile');
// 	var fileName = $file.val();
// 	$('#brandFileName').html(fileName);
// }

// function displayUploadData(){
//  	resetUploadDialog(); 	
// 	$('#upload-brand-modal').modal('toggle');
// }

function displayBrand(data){
	$("#brand-edit-form input[name=brand]").val(data.brand);	
	$("#brand-edit-form input[name=category]").val(data.category);	
	$("#brand-edit-form input[name=quantity]").val(data.quantity);	
    $("#brand-edit-form input[name=revenue]").val(data.revenue);	
	$('#edit-brand-modal').modal('toggle');
}
function downloadCsvFile(data){
	var url = getSalesReportUrl() + "/exportCsv";
	window.location.href = url;
}

//INITIALIZATION CODE
function init(){
	getBrandList();
	$('#inputProductBrandName').change(categoryOption);
	$('#get-sales-report').click(addBrand);
	$('#download-csv').click(downloadCsvFile);
	$('#refresh-data').click(getOrderList);
}

$(document).ready(init);
$(document).ready(addBrand);

