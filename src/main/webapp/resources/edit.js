let path = null;
let userId = null;
let taskId = null;
let task = null;
let statusList = null;
let tagList = null;
let id= null;
let createdBy = null;
let title = null;
let description = null;
let taskStatus = null;
let hours = null;
let createdAt = null;
let updatedAt = null;
let plannedStart = null;
let plannedEnd = null;
let actualStart = null;
let actualEnd = null;
let content = null;
let tag = null;
let commentList = null;

$('#edit_return').click(function () {
 window.location.href = '../pages/tasks.html';
})

function getCook(cookiename)
{
    var cookiestring=RegExp(cookiename+"=[^;]+").exec(document.cookie);
    return decodeURIComponent(!!cookiestring ? cookiestring.toString().replace(/^[^=]+./,"") : "");
}
function firstLoad() {
    $('.comment-page').hide();
    userId=getCook('userId');
    $('#id_right').text(userId);
    taskId = $.urlParam('id');
    loadTags();
    loadStatuses();
    if(taskId!=null) {
        loadTaskFields();
    }
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
    for (let i = 0; i < tagList.length; i++) {
        $("#task_tag").append("<option value=\""+(tagList[i].id)+"\">"+ tagList[i].title +"</option>");
    }
}
function loadStatuses() {
    $.ajax({
        url: "/status",
        type: "GET",
        async: false,
        success: function (data) {
            statusList = data.statuses;
        },
        contentType: "application/json",
        dataType: 'json'
    });
    for (let i = 0; i < statusList.length; i++) {
        $("#task_status").append("<option value=\""+statusList[i]+"\">"+ statusList[i] +"</option>");
    }
}

function loadTaskFields() {
    let queryUrl =  "/tasks/" + taskId;
    $.ajax({
        url: queryUrl,
        type: "GET",
        async: false,
        success: function (data) {
            task = data;
        },
        contentType: "application/json",
        dataType: 'json'
    });
    id = task.id;
    createdBy = task.createdBy.id;
    title = task.title;
    description = task.description;
    taskStatus = task.status+"";
    hours = task.hours;
    createdAt = task.createdAt;
    updatedAt = task.updatedAt;
    plannedStart = task.plannedStart;
    plannedEnd = task.plannedEnd;
    actualStart = task.actualStart;
    actualEnd = task.actualEnd;
    content = task.content;
    tag = task.tag.id;
    updateTaskFields();
}
function updateTaskFields() {
    if(id!=null) { $('#task_id').val(id); }
    if(createdBy!=null) { $('#task_created_by').val(createdBy); }
    if(title!=null) { $('#task_title').val(title); }
    if(description!=null) { $('#task_description').val(description); }
    if(content!=null) { $('#task_content').val(description); }
    if(taskStatus!=null) { $('#task_status').val(taskStatus); }
    if(tag!=null) { $("#task_tag").val(tag); }
    if(hours!=null) { $('#task_hours').val(hours); }
    if(createdAt!=null) { $('#task_created').val(createdAt); }
    if(updatedAt!=null) { $('#task_updated').val(updatedAt); }
    if(plannedStart!=null) { $('#task_plannedStart').val(plannedStart); }
    if(plannedEnd!=null) { $('#task_plannedEnd').val(plannedEnd); }
    if(actualStart!=null) { $('#task_actualStart').val(actualStart); }
    if(actualEnd!=null) { $('#task_actualEnd').val(actualEnd); }
}

$.urlParam = function(name){
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results==null) {
        return null;
    }
    return decodeURI(results[1]) || 0;
}

function doSave() {
    let updTitle = $('#task_title').val();
    let updHours = $('#task_hours').val();
    let updDescription = $('#task_description').val();
    let updContent = $('#task_content').val();
    let updStatus = $('#task_status option:selected').text();
    let updTag = $('#task_tag option:selected').val();
    let updTagText = $('#task_tag option:selected').text();
    let plnBegin = $('#task_plannedStart').val();
    let plnEnd = $('#task_plannedEnd').val();
    let actBegin = $('#task_actualStart').val();
    let actEnd = $('#task_actualEnd').val();
    if (updTitle===title || updTitle==="") updTitle=null;
    if (updHours===hours || updHours==="") updHours=null;
    if (updDescription===description || updDescription==="") updDescription=null;
    if (updContent===content || updContent==="") updContent=null;
    if (updStatus===taskStatus || updStatus==="Выберите Статус") updStatus=null;
    if (updTag==="0") updTag=null;
    if (plnBegin===plannedStart || plnBegin==="") plnBegin=null;
    if (plnEnd===plannedEnd || plnEnd==="") plnEnd=null;
    if (actBegin===actualStart || actBegin==="") actBegin=null;
    if (actEnd===actualEnd || actEnd==="") actEnd=null;
    let request = JSON.stringify({title:updTitle, hours:updHours, description:updDescription, content:updContent, status:updStatus, tagId:updTag,
        plannedStart:plnBegin, plannedEnd:plnEnd, actualStart:actBegin, actualEnd:actEnd});
    if (taskId==null) {
        if(updTag===null || updTitle===null || updHours===null || updStatus==null) {
            alert("Не заполнены необходимый поля!")
        } else {
            sendPost(request);
            window.location.href='../pages/tasks.html';
        }
    }
    else {
        sendPut(request)
    }
}
function sendPost(req) {
    $.ajax({
        url: "/tasks",
        type: "POST",
        async: false,
        data: req,
        success: function (data) {
            alert("Задача успешно сохранена!")
        },
        contentType: "application/json",
        dataType: 'json'
    });
}
function sendPut(req) {
    $.ajax({
        url: "/tasks/"+taskId,
        type: "PUT",
        async: false,
        data: req,
        success: function (data) {
            alert("Задача успешно обновлена!")
        },
        contentType: "application/json",
        dataType: 'json'
    });
}

function addComment() {
    let comTitle = $('#new_comment_title').val();
    let comContent = $('#new_comment_content').val();
    if (taskId!=null && userId!=null) {
        if (comTitle === "" || comContent === "") {
            alert("Не все поля заполнены!")
        } else {
            let request = JSON.stringify({title: comTitle, content: comContent, taskId: taskId, userId: userId});
            $.ajax({
                url: "/comments",
                type: "POST",
                async: false,
                data: request,
                success: function (data) {
                    drawComment(data);
                },
                contentType: "application/json",
                dataType: 'json'
            });
        }
    }
}

function drawComment(comment) {
    let str = "<tr class=\"deletable_tr\">" +
        "<td class=\"deleteble_td comments_id\">" + comment.id + "</td>" +
        "<td class=\"deleteble_td comments_created\">" + comment.createdAt + "</td>" +
        "<td class=\"deleteble_td comments_user\">" + comment.user.userName + "</td>" +
        "<td class=\"deleteble_td comments_title\">" + comment.title + "</td>" +
        "<td class=\"deleteble_td comments_data\">" + comment.content + "</tr>";

    $('#comment_body').prepend(str);
}
function drawComments() {
    $.ajax({
        url: "/comments/"+taskId,
        type: "GET",
        async: false,
        success: function (data) {
            commentList=data;
        },
        contentType: "application/json",
        dataType: 'json'
    });
    $(".deletable_td").remove();
    $(".deletable_tr").remove();
    for (let i = 0; i < commentList.length; i++) {
        let str = "<tr class=\"deletable_tr\">" +
            "<td class=\"deleteble_td comments_id\">" + commentList[i].id + "</td>" +
            "<td class=\"deleteble_td comments_created\">" + commentList[i].createdAt + "</td>" +
            "<td class=\"deleteble_td comments_user\">" + commentList[i].user.userName + "</td>" +
            "<td class=\"deleteble_td comments_title\">" + commentList[i].title + "</td>" +
            "<td class=\"deleteble_td comments_data\">" + commentList[i].content + "</tr>";

        $('#comment_body').append(str);
    }
}

$('#edit_comments').click(function () {
    $('.comment-page').show();
    drawComments();
})
$('#add_comment').click(function () {
    addComment();
})
