

function getDailySalesUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/scheduler";
}


function getDailySalesList(){
	var url = getDailySalesUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayDailySalesList(data);  
	   },
	   error: handleAjaxError
	});
}


//UI DISPLAY METHODS

function displayDailySalesList(data){
	var $tbody = $('#daily-sales-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		// var buttonHtml = '<button class="fa fa-trash" style="border:0px;" aria-hidden="true"  onclick="deleteBrand(' + e.id + ')"></button>'
		// var buttonHtml = ' <button class="fa fa-pencil" style="border:0.5px;border-color:grey" aria-hidden="true" onclick="displayEditBrand(' + e.id + ')"></button>'
		var row = '<tr>'
		+ '<td>' + e.date + '</td>'
		+ '<td>' + e.invoiced_orders_count + '</td>'
		+ '<td>'  + e.invoiced_items_count + '</td>'
		+ '<td>'  + e.total_revenue + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

// function displayEditDailySales(id){
// 	var url = getDailySalesUrl() + "/" + id;
// 	$.ajax({
// 	   url: url,
// 	   type: 'GET',
// 	   success: function(data) {
// 	   		displayDailySales(data);   
// 	   },
// 	   error: handleAjaxError
// 	});	3
// }


function displayUploadData(){
 	resetUploadDialog(); 	
	$('#upload-brand-modal').modal('toggle');
}

// function displayDailySales(data){
// 	$("#brand-edit-form input[name=date]").val(data.date);	
// 	$("#brand-edit-form input[name=invoiced_orders_count]").val(data.invoiced_orders_count);	
// 	$("#brand-edit-form input[name=invoiced_items_count]").val(data.invoiced_items_count);
// 	$("#brand-edit-form input[name=total_revenue]").val(data.total_revenue);	
// 	$('#edit-brand-modal').modal('toggle');
// }


//INITIALIZATION CODE
function init(){
	$('#refresh-data').click(getDailySalesList);
	$('#upload-data').click(displayUploadData);
}

$(document).ready(init);
$(document).ready(getDailySalesList);

