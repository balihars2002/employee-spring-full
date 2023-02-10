function getProductUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/";
}
function getRole(){
	var role = $("meta[name=userRole]").attr("content")
	console.log(" role :: ",role);
	return role;
}
function getBrandUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/operator/brand";
}

//BUTTON ACTIONS
function addProduct(event) {
    //Set the values to update
    var url = getProductUrl()+"product";
    console.log("url is : ", url);
    var $form = $("#product-form");
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
            Toastify({
                text: "Product added Successfully",
                style: {
                    background: "linear-gradient(to right,  #5cb85c, #5cb85c)",
                  },
                duration: 3000
                }).showToast();
            getProductList();
            $form.trigger("reset");
        },
        error: handleAjaxError
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
            console.log("tostification");
            Toastify({
                text: "Product updated Successfully",
                style: {
                    background: "linear-gradient(to right,  #5cb85c, #5cb85c)",
                  },
                duration: 3000
                }).showToast();
            getProductList();
        },
        error: handleAjaxError
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
            Toastify({
                text: "Product deleted Successfully",
                style: {
                    background: "linear-gradient(to right,  #5cb85c, #5cb85c)",
                  },
                duration: 3000
                }).showToast();
            getProductList();
        },
        error: handleAjaxError
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
function uploadRows() {

    //Update progress
    updateUploadDialog();
    //If everything processed then return
    if (processCount == fileData.length) {
        if (errorData.length == 0) {
            $('#upload-product-modal').modal('hide');
        }
        getProductList();
        return;
    }

    //Process next row
    var row = fileData[processCount];
    processCount++;

    var json = JSON.stringify(row);
    var url = getProductUrl()+"product";

    //Make ajax call
    $.ajax({
        url: url,
        type: 'POST',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            uploadRows();
        },
        error: function (response) {
            row.error = response.responseText
            errorData.push(row);
            uploadRows();
        }
    });

}

function downloadErrors() {
    writeFileData(errorData);
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

//UI DISPLAY METHODS

function displayProductList(data) {
    var $tbody = $('#product-table').find('tbody');
    $tbody.empty();
    for (var i in data) {
        var e = data[i];
        console.log("data: ", data);
        var buttonHtml = '<button class="fa fa-trash" title="delete product" onclick="deleteProduct(' +e.id + ')" id = "' +e.id + '" value="' + e.barcode + '"></button>'
        buttonHtml += ' <button class="fa fa-pencil" title="edit product" onclick="displayEditProduct(' + e.id + ')"></button>'
        var row = '<tr>'
            + '<td>' + e.id + '</td>'
            + '<td>' + e.barcode + '</td>'
            + '<td>' + e.brand + '</td>'
            + '<td>' + e.category + '</td>'
            + '<td>' + e.mrp + '</td>'
            + '<td>' + e.name + '</td>'
            + '<td>' + buttonHtml + '</td>'
            + '</tr>';
        $tbody.append(row);
    }
    if(getRole()=="operator"){
		deleteEditButton();
	}
}

function deleteEditButton(){
	var help = document.getElementById("product-table");

		var rowcount = help.rows;
		console.log("the number of rows ::",rowcount);
		console.log(help);
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
            console.log("int to the update product 4");
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
    $('#add-product').click(addProduct);
    $('#update-the-product').click(updateTheProduct);
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
$(document).ready(processDropDown);
