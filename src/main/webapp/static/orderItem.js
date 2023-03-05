
function getOrderItemUrl() {
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/operator/orderItem";
}

function getOrderUrl() {
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/operator/order";
}

function addOrderItem(event) {
	var $form = $("#orderItem-form");
	var json = toJson($form);
	var url = getOrderItemUrl();

	$.ajax({
		url: url,
		type: 'POST',
		data: json,
		headers: {
			'Content-Type': 'application/json'
		},
		success: function (response) {
			getOrderList();
		},
		error: handleAjaxError
	});

	return false;
}

function updateOrderItem(event) {

	var id = $("#orderItem-edit-form input[name=id]").val();
	console.log("id :: ", id);
	var url = getOrderItemUrl() + "/" + id;
	console.log("url :: ", url);
	var $form = $("#orderItem-edit-form");
	var json = toJson($form);

	$.ajax({
		url: url,
		type: 'PUT',
		data: json,
		headers: {
			'Content-Type': 'application/json'
		},
		success: function (response) {
			$('#edit-orderItem-modal').modal('toggle');
			msgSuccess("Order updated");
			getOrderList();
		},
		error: function (response) {
			msgError(response.responseText);
			handleAjaxError
		}
	});

	return false;
}

function deleteOrderItem(id) {
	var url = getOrderItemUrl() + "/" + id;
	$.ajax({
		url: url,
		type: 'DELETE',
		success: function (data) {
			msgSuccess("Item removed");
			getOrderList();
		},
		error: function (response) {
			msgError(response.responseText);
			handleAjaxError
		}
	});
}


function displayOrderItemList(data) {
	var $tbody = $('#order-item-table').find('tbody');
	console.log("into the display function");
	$tbody.empty();
	var sno = 1;
	for (var i in data) {
		var e = data[i];
		var buttonHtml = '<button class="fa fa-trash" style="border:3px;border-color:black" aria-hidden="true"  onclick="deleteOrderItem(' + e.id + ')"></button>'
		buttonHtml = ' <button class="fa fa-pencil" style="border:8px;border-color:black" aria-hidden="true" onclick="displayEditOrderItem(' + e.id + ')"></button>'
		var row = '<tr>'
			+ '<td>' + sno + '</td>'
			+ '<td>' + e.barcode + '</td>'
			+ '<td>' + e.quantity + '</td>'
			+ '<td>' + e.sellingPrice + '</td>'
			+ '<td>' + buttonHtml + '</td>'
			+ '</tr>';
		$tbody.append(row);
		sno += 1;
	}
}
function getOrderList() {
	console.log("into the function getORderList");
	var url = document.location.href;
	var params = url.split('/');
	var order_id = params.at(-1);
	var url = getOrderUrl() + "/" + order_id;
	console.log("url is :: ", url);
	$.ajax({
		url: url,
		type: 'GET',
		success: function (data) {
			console.log(data);
			displayOrderItemList(data);
		},
		error: handleAjaxError
	});
}
function displayEditOrderItem(id) {
	var url = getOrderItemUrl() + "/" + id;
	$.ajax({
		url: url,
		type: 'GET',
		success: function (data) {
			displayOrderItem(data);
		},
		error: handleAjaxError
	});
}

function displayOrderItem(data) {
	$("#orderItem-edit-form input[name=id]").val(data.id);
	$("#orderItem-edit-form input[name=barcode]").val(data.barcode);
	$("#orderItem-edit-form input[name=quantity]").val(data.quantity);
	$("#orderItem-edit-form input[name=sellingPrice]").val(data.sellingPrice);
	$('#edit-orderItem-modal').modal('toggle');
}

function init() {
	$('#add-orderItem').click(addOrderItem);
	$('#update-the-orderItem').click(updateOrderItem);
}

$(document).ready(init);
$(document).ready(getOrderList);

