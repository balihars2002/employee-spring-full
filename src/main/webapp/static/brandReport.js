
function getBrandReportUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/brandReport";
}

function getBrandUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/operator/brand";
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


function processDropDown() {
    var url = getBrandUrl();
    var brandData = null;
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

	    let brandArr = [], categoryArr = [];
	    data.forEach((e) => {
	        brandArr.push(e.brand);
	        categoryArr.push(e.category);
	    });
	
	    brandArr = [...new Set(brandArr)];
	    categoryArr = [...new Set(categoryArr)];
	
	    // BRAND
	    var $brandDropdown = $('#brand-dropdown-menu');
	    $brandDropdown.empty();
	    var $editBrandDropdown = $('#edit-brand-dropdown-menu');
	    $editBrandDropdown.empty();
	
	    var firstRowBrand = '<option value="none" selected disabled>Select Brand</option>';
	    $brandDropdown.append(firstRowBrand);
	
	    brandArr.forEach((brand) => {
	        var row = '<option value="' + brand + '">' + brand + '</option>';
	        $brandDropdown.append(row);
	        $editBrandDropdown.append(row);
	    })
	
	    // CATEGORY
	    var $categoryDropdown = $('#category-dropdown-menu');
	    $categoryDropdown.empty();
	    var $editCategoryDropdown = $('#edit-category-dropdown-menu');
	    $editCategoryDropdown.empty();
	
	    var firstRowCategory = '<option value="none" selected disabled hidden>Select Category</option>';
	    $categoryDropdown.append(firstRowCategory);
	
	    categoryArr.forEach((category) => {
	        var row = '<option value="' + category + '">' + category + '</option>';
	        $categoryDropdown.append(row);
	        $editCategoryDropdown.append(row);
	    })
		
	}
function displayBrandList(data){
	var $tbody = $('#brandReport-table').find('tbody');
	$tbody.empty();
	var sno = 1;
	for(i = data.length-1 ; i>=0 ; i--){
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
$(document).ready(processDropDown);

