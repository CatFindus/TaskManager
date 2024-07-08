let tagList = null;

function firstLoad() {
    userId=getCook('userId');
    $('#id_right').text(userId);
    loadTags();
    redrawTags();
}
function loadTags() {
    $.ajax({
        url: "/tag",
        type: "GET",
        async: false,
        success: function (data) {
            tagList = data;
        },
        contentType: "application/json",
        dataType: 'json'
    });
}
function redrawTags() {
    $(".deletable_td").remove();
    $(".deletable_tr").remove();

    for (let i = 0; i < tagList.length; i++) {
        let text_id= 'text_'+tagList[i].id;
        let color_id= 'color_'+tagList[i].id;
        let button_id = 'button_'+tagList[i].id;
        let str = "<tr class=\"deletable_tr\">" +
            "<td class=\"deleteble_td tag_id\">" + tagList[i].id + "</td>" +
            "<td class=\"deleteble_td tag_title\">" + "<input type=\"text\" id=\""+text_id+"\" class=\"table_text\">" + "</td>" +
            "<td class=\"deleteble_td tag_color\">" + "<input type=\"color\" id=\""+color_id+"\" class=\"table_color\">" + "</td>" +
            "<td class=\"deleteble_td tag_update\">" + "<button type=\"button\" id=\""+button_id+"\" class=\"table_button\" onclick=\"modifyTag("+tagList[i].id+")\">Изменить</button>" + "</td>" + "</tr>";
        $('#tag_body').prepend(str);
        $('#'+text_id).val(tagList[i].title);
        $('#'+color_id).val(tagList[i].slug);
    }
}
function addTag() {
    let title = $('#tag_title').val();
    let color = $('#tag_color').val();
    let str = JSON.stringify({title:title, slug:color});
    $.ajax({
        url: "/tag",
        type: "POST",
        data: str,
        async: false,
        success: function (data) {
            let text_id= 'text_'+data.id;
            let color_id= 'color_'+data.id;
            let button_id = 'button_'+data.id;
            let str = "<tr class=\"deletable_tr\">" +
                "<td class=\"deleteble_td tag_id\">" + data.id + "</td>" +
                "<td class=\"deleteble_td tag_title\">" + "<input type=\"text\" id=\""+text_id+"\" class=\"table_text\">" + "</td>" +
                "<td class=\"deleteble_td tag_color\">" + "<input type=\"color\" id=\""+color_id+"\" class=\"table_color\">" + "</td>" +
                "<td class=\"deleteble_td tag_update\">" + "<button type=\"button\" id=\""+button_id+"\" class=\"table_button\" onclick=\"modifyTag("+data.id+")\">Изменить</button>" + "</td>" + "</tr>";
            $('#tag_body').prepend(str);
            $('#'+text_id).val(data.title);
            $('#'+color_id).val(data.slug);
        },
        contentType: "application/json",
        dataType: 'json'
    });
}
function modifyTag(id) {
    let text_id= 'text_'+id;
    let color_id= 'color_'+id;
    let updTitle = $('#'+text_id).val();
    let updSlug = $('#'+color_id).val();
    if (updSlug===tagList[id].slug || updSlug==="") updSlug=null;
    if (updTitle===tagList[id].title || updTitle==="") updTitle=null;
    if (updSlug!=null || updTitle!=null) {
        let str = JSON.stringify({title:updTitle, slug:updSlug});
        $.ajax({
            url: "/tag/"+id,
            type: "PUT",
            data: str,
            async: false,
            success: function (data) {
                alert("Изменено успешно!");
            },
            contentType: "application/json",
            dataType: 'json'
        });
    }
}

function getCook(cookiename)
{
    var cookiestring=RegExp(cookiename+"=[^;]+").exec(document.cookie);
    return decodeURIComponent(!!cookiestring ? cookiestring.toString().replace(/^[^=]+./,"") : "");
}