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
            var data = "User created";
            Toastify({
                text:data ,
                style: {
                    background: "linear-gradient(to right,  #5cb85c, #5cb85c)",
                },
                duration: 2500
            }).showToast();
        },
        error: function (response) {
            Toastify({
                text:JSON.parse(response.responseText).message,
                style: {
                    background: "linear-gradient(to right,  #e74c3c, #e74c3c)",
                },
                duration: 2500
            }).showToast();
            handleAjaxError
        }
    });
}

function init() {
    $('#add-user').click(signup);
}

$(document).ready(init);

