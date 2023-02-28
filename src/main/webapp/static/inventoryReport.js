
function getInventoryUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/inventoryReport";
}

function getBrandUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/";
}

function getInventoryList(){
	var url = getInventoryUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayInventoryList(data);  
	   },
	   error: handleAjaxError
	});
}


function addInventory(event){
	//Set the values to update
	console.log("JSON :::");
	var $form = $("#inventory-report-form");
	var json = toJson($form);
	var url = getInventoryUrl();
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
        displayInventoryList(response);  
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

function displayInventoryList(data){
	var $tbody = $('#inventoryReport-table').find('tbody');
	$tbody.empty();
	var sno = 1;
	for(var i in data){
		var e = data[i];
		var row = '<tr>'
		+ '<td>' + sno + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>' + e.category + '</td>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>' + e.mrp + '</td>'
		+ '<td>' + e.name + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '</tr>';
        $tbody.append(row);
		sno += 1;
	}
}

function downloadCsvFile(data){
	console.log(" into the function ");
	var url = getInventoryUrl() + "/exportCsv";
	console.log( " url :: ",url);
	window.location.href = url;
}

//INITIALIZATION CODE
function init(){
	getBrandList();
	$('#inputProductBrandName').change(categoryOption);
	$('#get-inventory-report').click(addInventory);
	$('#download-csv').click(downloadCsvFile);
}

$(document).ready(init);
$(document).ready(addInventory);



