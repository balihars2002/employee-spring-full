function signup(event) {
    var $form = $("#signup-form");
    var json = toJson($form);
    var baseUrl = $("meta[name=baseUrl]").attr("content");
    var url = baseUrl + "/site/init";
    //var orderurl = baseUrl + "/ui/order";
    
    console.log("url is :", url);
    console.log("JSON : ", json);
    $.ajax({
        url: url,
        type: 'POST',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            // $(location).prop('href', '/employee/session/login');
        },
        error: function (response) {
           if(JSON.parse(response.responseText).message == "User added successfully"){
            Toastify({
                text:JSON.parse(response.responseText).message,
                style: {
                    background: "linear-gradient(to right,  #5cb85c, #5cb85c)",
                },
                duration: 2500
            }).showToast();
           }
           else{
            Toastify({
                text:JSON.parse(response.responseText).message,
                style: {
                    background: "linear-gradient(to right,  #e74c3c, #e74c3c)",
                },
                duration: 2500
            }).showToast();
           }
            
            handleAjaxError
        }
    });
}

function init() {
    $('#add-user').click(signup);
}

$(document).ready(init);
