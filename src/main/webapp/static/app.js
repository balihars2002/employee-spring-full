

function getRole(){
	var role = $("meta[name=userRole]").attr("content")
	console.log(" role :: ",role);
	return role;
}

//HELPER METHOD
function toJson($form){
    var serialized = $form.serializeArray();
    console.log(serialized);
    var s = '';
    var data = {};
    for(s in serialized){
        data[serialized[s]['name']] = serialized[s]['value']
    }
    var json = JSON.stringify(data);
    return json;
}

$(document).ready(function() {
    $('.nav-item active').removeClass('active').removeAttr('aria-current');
    $('a[href="' + location.pathname + '"]').closest('li').addClass('active').attr('aria-current', 'page');
});

function handleAjaxError(response){
	var response = JSON.parse(response.responseText);
	alert(response.message);
}

function readFileData(file, callback){
	var config = {
		header: true,
		delimiter: "\t",
		skipEmptyLines: "greedy",
		complete: function(results) {
			callback(results);
	  	}	
	}
	Papa.parse(file, config);
}


function writeFileData(arr){
	var config = {
		quoteChar: '',
		escapeChar: '',
		delimiter: "\t"
	};
	
	var data = Papa.unparse(arr, config);
    var blob = new Blob([data], {type: 'text/tsv;charset=utf-8;'});
    var fileUrl =  null;

    if (navigator.msSaveBlob) {
        fileUrl = navigator.msSaveBlob(blob, 'download.tsv');
    } else {
        fileUrl = window.URL.createObjectURL(blob);
    }
    var tempLink = document.createElement('a');
    tempLink.href = fileUrl;
    tempLink.setAttribute('download', 'download.tsv');
    tempLink.click(); 
}
function disableButtons(){
    if(getRole()=="operator"){
        $("#user-link").addClass('d-none');
        document.getElementById("dropdownMenuButton").hidden = true;
        document.getElementById("brand-report").hidden = true;
        document.getElementById("sales-report").hidden = true;
        document.getElementById("inventory-report").hidden = true;
        document.getElementById("daily-sales").hidden = true;
        // document.getElementById("user").hidden = true;
        
    }
}

function msgSuccess(data){
    Toastify({
        text: data,
        style: {
            background: "linear-gradient(to right,  #5cb85c, #5cb85c)",
          },
        duration: 2500
        }).showToast();
}

function msgError(data){
    Toastify({
        text: JSON.parse(data).message,
        style: {
            background: "linear-gradient(to right,  #e74c3c, #e74c3c)",
          },
        duration: 2500
        }).showToast();
}

function msgErrorstring(data){
    Toastify({
        text: data,
        style: {
            background: "linear-gradient(to right,  #e74c3c, #e74c3c)",
          },
        duration: 2500
        }).showToast();
}

$(document).ready(disableButtons);