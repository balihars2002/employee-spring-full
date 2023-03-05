
function getUserUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/user";
}


function getRole(){
	var role = $("meta[name=userRole]").attr("content")
	console.log(" role :: ",role);
	return role;
}

function addUser(event){
	var $form = $("#user-form");
	var json = toJson($form);
	var url = getUserUrl();
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
		console.log("entered");
		$("#add-user-modal").modal('toggle');
		$("#add-user-modal").modal('reset');
		Toastify({
			text: "User added",
			style: {
				background: "linear-gradient(to right,  #5cb85c, #5cb85c)",
			  },
			duration: 2500
			}).showToast();
	   		getUserList();    
	   },
	   error: function(response){
		msgError(response.responseText);
		handleAjaxError
	   }
	});
	
	return false;
}

function getUserList(){
	var url = getUserUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayUserList(data);   
	   },
	   error: function(response){
		msgError(response.responseText);
		handleAjaxError
	   }
	});
}

function deleteUser(id){
	var url = getUserUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
		Toastify({
			text: "User deleted Successfully",
			style: {
				background: "linear-gradient(to right,  #5cb85c, #5cb85c)",
			  },
			duration: 2500
			}).showToast();
	   		getUserList();    
	   },
	   error: handleAjaxError
	});
}


function displayUserList(data){
	console.log('Printing user data');
	var $tbody = $('#user-table').find('tbody');
	$tbody.empty();
	var sno = 1;
	for(i = data.length-1 ; i>=0 ; i--){
		var e = data[i];
		var status;
		var buttonHtml = ' <button class="fa fa-ban" id="edit-button"  data-toggle="tooltip" data-html="true" title="Disable User" style="color:red;border-radius :5px;border-color:grey" aria-hidden="true" onclick="disableUser(' + e.id + ')"></button>'
		if(e.disabled){
			buttonHtml = ''
			status = "Disabled";
		}
		else{
			status = "Active";
		}
		if(e.role == "supervisor"){
			// console.log("fkjdjf")
			// $("#edit-button").hide();
			buttonHtml = ''
		}
		var row = '<tr>'
		+ '<td>' + sno + '</td>'
		+ '<td>' + e.email + '</td>'
		+ '<td>' + e.role + '</td>'
		+ '<td>' + status + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
		sno += 1;
	}
	
}



function myFunction() {
	var x = document.getElementById("password");
	if (x.type === "password") {
		x.type = "text";
	} else {
		x.type = "password";
	}
}

function disableUser(id){
	var url = getUserUrl() + "/" + id;
	$.ajax({
		url: url,
		type: 'PUT',
		// data: json,
		headers: {
			'Content-Type': 'application/json'
		},	   
		success: function(response) {
			msgSuccess("User Disabled");
			getUserList();   
		},
		error: function(response){
		 msgError(response.responseText);
		 handleAjaxError
		}
	 });

}

function addToggle(event){
	$('#add-user-modal').modal('toggle');
}

//INITIALIZATION CODE
function init(){
	$('#show-password').click(myFunction);
	$('#add-user-button').click(addToggle);
	$('#add-user').click(addUser);
	$('#refresh-data').click(getUserList);
}

$(document).ready(init);
$(document).ready(getUserList);

