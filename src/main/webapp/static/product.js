function getProductUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/";
}
function getRole(){
	var role = $("meta[name=userRole]").attr("content")
	console.log(" role :: ",role);
	return role;
}
function getBrandUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/";
}

//BUTTON ACTIONS
function addProduct(event) {
    //Set the values to update
    var url = getProductUrl()+"product";
    console.log("url is : ", url);
    var $form = $("#product-add-form");
    var json = toJson($form);
    console.log("json : : -> : ", json);
    $.ajax({
        url: url,
        type: 'POST',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            $('#add-product-modal').modal('toggle');
            msgSuccess("Product added Successfully");                
            getProductList();
            $form.trigger("reset");
        },
        error: function(response){
            msgError(response.responseText);
            handleAjaxError
           }
    });

    return false;
}

function updateTheProduct(event) {
    console.log("int to the update product 5");
    $('#edit-product-modal').modal('toggle');

    //Get the ID
    var id = $("#product-edit-form input[name=id]").val();
    var url = getProductUrl() + "product/" + id;
    console.log("the url in the updateproduct function is : ",  url );
    //Set the values to update
    var $form = $("#product-edit-form");
    var json = toJson($form);
    console.log(" the json **** :",json);
    $.ajax({
        url: url,
        type: 'PUT',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            msgSuccess("Product updated Successfully");
            getProductList();
        },
        error: function(response){
            msgError(response.responseText);
            handleAjaxError
        }
    });

    return false;
}


function getProductList() {
    var url = getProductUrl()+"operator/product";
    console.log("url: ", url);

    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            console.log("got result : ", data);
            displayProductList(data);
        },
        error: handleAjaxError
    });
}

function deleteProduct(id) {
   // var barcode = $('#'+id)[0].value;
    var url = getProductUrl() + "product/" + id;
    console.log(" url is : ", url);

    $.ajax({
        url: url,
        type: 'DELETE',
        success: function (data) {
            msgSuccess("Product deleted Successfully")
            getProductList();
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


function processData() {
    var file = $('#productFile')[0].files[0];
    readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results) {
    fileData = results.data;
    uploadRows();
}

// Recursive Function

function uploadFileHelper(data){
	console.log("second");
	json = JSON.stringify(data); 
	var url = getProductUrl() + "product/tsv";
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	   		//uploadRows();  
	   },
	   error: function(response){
	   		//row.error=response.responseText
	   		 //errorData.push(row);
	   		//uploadRows();
	   }
	});
}

var array = [];
function uploadRows(){

	console.log("first");

	updateUploadDialog();
	while(1){
	if(processCount==fileData.length){
		break;
	}
	
	var row = fileData[processCount];
	processCount++;
	
	var json = JSON.stringify(row);
	console.log("json :: ",json);
	array.push(row);
	
	}
	console.log("consoling array::");
	console.log(array);

	uploadFileHelper(array);
}

function downloadErrors() {
    writeFileData(errorData);
}

// function processDropDown() {
//     var url = getBrandUrl();
//     var brandData = null;
//     $.ajax({
//         url: url,
//         type: 'GET',
//         success: function (data) {
//             fillDropDown(data);
//         },
//         error: handleAjaxError
//     });
// }

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
// function editbrandOption(data) {
//     data.sort(sortBrand);
//     let editSelectTag = $('#editProductBrandName');
//     editSelectTag.empty();
//     for (let i in data) {
//         let e = data[i];
//         editbrands.add(e.brand);
//         if (editbrandsAndCategory[e.brand])
//             editbrandsAndCategory[e.brand].add(e.category);
//         else
//             editbrandsAndCategory[e.brand] = new Set([e.category]);
//     }
//     for (brandName of editbrands.values()) {
//         let option = $('<option></option>').attr("value", brandName).text(brandName);
//         editSelectTag.append(option);
//     }
//     editcategoryOption();
// }
// function editcategoryOption() {
//     let bd = $('#editProductBrandName')[0].value;
//     let editSelectTag = $("#editProductBrandCategoryName");
//     editSelectTag.empty();
//     for (categoryName of editbrandsAndCategory[bd].values()) {
//         let option1 = $('<option></option>').attr("value", categoryName).text(categoryName);
//         editSelectTag.append(option1);
//     }
// }

// function fillDropDown(data) {

//     let brandArr = [], categoryArr = [];
//     data.forEach((e) => {
//         brandArr.push(e.brand);
//         categoryArr.push(e.category);
//     });

//     brandArr = [...new Set(brandArr)];
//     categoryArr = [...new Set(categoryArr)];

//     // BRAND
//     var $brandDropdown = $('#brand-dropdown-menu');
//     $brandDropdown.empty();
//     var $editBrandDropdown = $('#edit-brand-dropdown-menu');
//     $editBrandDropdown.empty();

//     var firstRowBrand = '<option value="none" selected disabled>Select Brand</option>';
//     $brandDropdown.append(firstRowBrand);

//     brandArr.forEach((brand) => {
//         var row = '<option value="' + brand + '">' + brand + '</option>';
//         $brandDropdown.append(row);
//         $editBrandDropdown.append(row);
//     })

//     // CATEGORY
//     var $categoryDropdown = $('#category-dropdown-menu');
//     $categoryDropdown.empty();
//     var $editCategoryDropdown = $('#edit-category-dropdown-menu');
//     $editCategoryDropdown.empty();

//     var firstRowCategory = '<option value="none" selected disabled hidden>Select Category</option>';
//     $categoryDropdown.append(firstRowCategory);

//     categoryArr.forEach((category) => {
//         var row = '<option value="' + category + '">' + category + '</option>';
//         $categoryDropdown.append(row);
//         $editCategoryDropdown.append(row);
//     })
    
// }

//UI DISPLAY METHODS

function displayProductList(data) {
    var $tbody = $('#product-table').find('tbody');
    $tbody.empty();
    var sno = 1;
    for (var i in data) {
        var e = data[i];
        console.log("data: ", data);
        // var buttonHtml = '<button class="fa fa-trash"  data-toggle="tooltip" data-html="true" title="delete product" style="border-radius :5px;border-color:grey" onclick="deleteProduct(' +e.id + ')" id = "' +e.id + '" value="' + e.barcode + '"></button>'
        var buttonHtml = ' <button class="fa fa-pencil"  data-toggle="tooltip" data-html="true" title="edit product" style="border-radius :5px;border-color:grey" onclick="displayEditProduct(' + e.id + ')"></button>'
        var row = '<tr>'
            + '<td>' + sno + '</td>'
            + '<td>' + e.barcode + '</td>'
            + '<td>' + e.brand + '</td>'
            + '<td>' + e.category + '</td>'
            + '<td>' + e.mrp + '</td>'
            + '<td>' + e.name + '</td>'
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
	var help = document.getElementById("product-table");
		var rowcount = help.rows;
		for(let i=0;i<rowcount.length ;i++){
			rowcount[i].deleteCell(6);
	}
}

function displayEditProduct(id) {
    //var barcode = $('#'+id)[0].value;
    console.log("the product is being displayed after editing");
    var url = getProductUrl() + "operator/product/" + id ;
    console.log(" url is : ", url);
   
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            displayProduct(data);
        },
        error: handleAjaxError
    });
}

function disableButtons(){
	var role = getRole();
	if(role == "operator"){
	document.getElementById("product-form").hidden = true;
	document.getElementById("upload-data").disabled = true;
	}
	// document.getElementById("add-brand").disabled = true;
}

function resetUploadDialog() {
    //Reset file name
    var $file = $('#productFile');
    $file.val('');
    $('#productFileName').html("Choose File");
    //Reset various counts
    processCount = 0;
    fileData = [];
    errorData = [];
    //Update counts	
    updateUploadDialog();
}

function updateUploadDialog() {
    $('#rowCount').html("" + fileData.length);
    $('#processCount').html("" + processCount - errorData.length);
    $('#errorCount').html("" + errorData.length);
}

// Adds File Path to the UI
function updateFileName() {
    var $file = $('#productFile');
    var fileName = $file.val();
    $('#productFileName').html(fileName);
}

function displayUploadData() {
    resetUploadDialog();
    $('#upload-product-modal').modal('toggle');
}

function addToggle(event){
    console.log("into the add");
	$('#add-product-modal').modal('toggle');
}

function cancelButton(){
	console.log("cancel function");
	$("#product-add-form").trigger("reset");
}

function displayProduct(data) {
    $("#edit-brand-dropdown-menu").val(data.brand);
    $("#edit-category-dropdown-menu").val(data.category);
    $("#product-edit-form input[name=id]").val(data.id);
    $("#product-edit-form input[name=barcode]").val(data.barcode);
    $("#product-edit-form input[name=name]").val(data.name);
    $("#product-edit-form input[name=mrp]").val(data.mrp);
    console.log("int to the update product 3");
    $('#edit-product-modal').modal('toggle');
}


//INITIALIZATION CODE

function init() {
    getBrandList();
    $('#inputProductBrandName').change(categoryOption);
    $('#cancel-top').click(cancelButton);
	$('#cancel-product').click(cancelButton);
    $('#add-product-button').click(addToggle);
    $('#update-the-product').click(updateTheProduct);
    $('#add-product').click(addProduct);
    $('#refresh-data').click(getProductList);
    $('#upload-data').click(displayUploadData);
    $('#process-data').click(processData);
    $('#download-errors').click(downloadErrors);
    $('#productFile').on('change', updateFileName);
    $("#brand-dropdown").change(function () {
        var selected = $(this).children("option:selected").val();
        alert("You have selected - " + selected);
    });
}

$(document).ready(disableButtons);
$(document).ready(init);
$(document).ready(getProductList);

// $(document).ready(processDropDown);
