let roles = null;
let path = null;
let user = null;

$('#profile_return').click(function () {
    window.location.href = '../pages/tasks.html';
})

function getUserData() {
    $.ajax({
        url: "/users/current",
        type: "GET",
        success: function (data) {
            user = data;
            if(user.id!=null) {
                updateFields();
            } else {
                path = user.location;
                window.location.href = path;
            }

        },
        contentType: "application/json",
        dataType: 'json'
    });
}

function updateFields() {
    $('#id_right').text(user.id);
    $('#profile_login').val(user.userName);
    if(user.firstName!=null) { $('#profile_firstName').val(user.firstName); }
    $('#role-select').val(user.role);
    if(user.middleName!=null) { $('#profile_middleName').val(user.middleName); }
    if(user.lastName!=null) { $('#profile_lastName').val(user.lastName); }
    if(user.firstName!=null) { $('#profile_firstName').val(user.firstName); }
    if(user.phone!=null) { $('#profile_phone').val(user.phone); }
    if(user.intro!=null) { $('#profile_intro').val(user.intro); }
    if(user.profile!=null) { $('#profile_profile').val(user.profile); }
}

function doLoad() {
    if(roles==null) {
        getRoles();
    }
    if(user==null) {
        getUserData();
    }
}

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
        $("#role-select").append("<option value=\""+rolesfield[i]+"\">"+ rolesfield[i] +"</option>");
    }
}

function updateProfile() {
    let firstName = $("#profile_firstName").val();
    let lastName = $("#profile_lastName").val();
    let middleName = $("#profile_middleName").val();
    let phone = $("#profile_phone").val();
    let intro = $("#profile_intro").val();
    let profile = $('#profile_profile').val();
    let password = $("#profile_pass").val();
    let role_id = $("#role-select option:selected").text();
    if (firstName==user.firstName || firstName==="") { firstName=null }
    if (lastName==user.lastName || lastName==="") { lastName=null }
    if (middleName==user.middleName || middleName==="") { middleName=null }
    if (phone==user.phone || phone==="") { phone=null }
    if (intro==user.intro || intro==="") { intro=null }
    if (profile==user.profile || profile==="") { profile=null }
    if (password==="") password=null;
    if (role_id==user.role || role_id==="") role_id=null;

    let request = JSON.stringify({firstName:firstName, middleName:middleName, lastName:lastName, passwordHash:password, role:role_id, intro:intro, profile:profile, phone:phone});
    $.ajax({
        url: "/users",
        type: "PUT",
        async: false,
        data: request,
        success: function (data) {
            logged = data.logged;
            if (logged) {
                path = data.location;
                window.location.href = '../pages/'+path;
            } else {
                path = data.location;
                window.location.href = '../'+path;
            }
        },
        contentType: "application/json",
        dataType: 'json'
    });
}

