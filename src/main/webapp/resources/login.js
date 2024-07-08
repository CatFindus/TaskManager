let roles = null;
let path = null;
let logged = null;

$('.message').click(function(){
    $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
});

function getRoles() {
    if (roles===null) {
        $.ajax({
            url: "/role",
            type: "GET",
            success: function (data) {
                roles = data;
                addRoles();
            },
            contentType: "application/json",
            dataType: 'json'
        });
    }
}

function addRoles() {
    let rolesfield = roles.roles;
    for (let i = 0; i < rolesfield.length; i++) {
        $("#role-select").append("<option value=\""+i+"\">"+ rolesfield[i] +"</option>");
    }
}

function doRegister() {
    let registerLogin = $('#register_login').val();
    let registerPass = $('#register_pass').val();
    let roleSelected = $('#role-select option:selected').text();
    let registerRequest = JSON.stringify({userName:registerLogin, passwordHash:registerPass, role:roleSelected});
    $.ajax({
        url: "/register",
        type: "POST",
        async: false,
        data: registerRequest,
        success: function (data) {
            logged = data.logged;
            if (logged) {
                path = data.location;
                window.location.href = path;
            } else {
                alert("Регистранция не удалась!");
            }
        },
        contentType: "application/json",
        dataType: 'json'
    });

}

function doLogin() {
    let loginLogin = $('#login_login').val();
    let loginPass = $('#login_pass').val();
    let loginRequest = JSON.stringify({userName:loginLogin, passwordHash:loginPass});
    $.ajax({
        url: "/login",
        type: "POST",
        async: false,
        data: loginRequest,
        success: function (data) {
            logged = data.logged;
            if (logged) {
                path = data.location;
                window.location.href = path;
            } else {
                alert("Вход не удался!");
            }
        },
        contentType: "application/json",
        dataType: 'json'
    });
}

