
function login(event) {
    var $form = $("#login-form");
    var json = toJson($form);
    var baseUrl = $("meta[name=baseUrl]").attr("content");
    var url = baseUrl + "/session/login";
    var orderurl = baseUrl + "/ui/order";
    console.log("url is :", orderurl);
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
            $(location).prop('href', '/employee/ui/order');
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
    $('#login').click(login);
}

$(document).ready(init);

