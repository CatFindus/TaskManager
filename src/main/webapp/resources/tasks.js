let taskCount = 0;
let tasks = [];
let filter = false;
let taskId="";
let taskTitle="";
let taskStatus="";
let taskBegin="";
let taskEnd="";
let taskTag="";
let tagColor = "";
let taskRequest;
let limit=10;
let offset=0;
let tagList;
let statuslist=null;


function drawTable() {
    $(".deletable_td").remove();
    $(".deletable_tr").remove();
    for (let i = 0; i < tasks.length; i++) {
        taskId = tasks[i].id==null ? "-" : tasks[i].id;
        taskTitle = tasks[i].title==null ? "-" : tasks[i].title;
        taskStatus = tasks[i].status==null ? "-" : tasks[i].status;
        taskBegin = tasks[i].plannedStart==null ? "-" : tasks[i].plannedStart;
        taskEnd = tasks[i].plannedEnd==null ? "-" : tasks[i].plannedEnd;
        taskTag = tasks[i].tag.title==null ? "-" : tasks[i].tag.title;
        tagColor = tasks[i].tag.slug;

        let str = "<tr class=\"deletable_tr\">" +
            "<td class=\"deleteble_td task_id\">"+taskId+"</td>"+
            "<td class=\"deleteble_td task_title\">"+taskTitle+"</td>"+
            "<td class=\"deleteble_td task_status\">"+taskStatus+"</td>"+
            "<td class=\"deleteble_td task_planned_begin\">"+taskBegin+"</td>"+
            "<td class=\"deleteble_td task_planned_end\">"+taskEnd+"</td>"+
            "<td class=\"deleteble_td task_tag\" bgcolor=\""+tagColor+"\">"+taskTag+"</td>"+
            "<td class=\"deleteble_td task_href\"><a href='/pages/edit.html?id="+ taskId +"'>Подробнее</a></td>"+"</tr>";

        $('#tablebody').append(str);
    }
}
function getCountForCurrent() {
    $.ajax({
        url: "/tasks/current/count?" + taskRequest,
        type: "GET",
        async: false,
        success: function (data) {
            taskCount = data.count;
        },
        contentType: "application/json",
        dataType: 'json'
    });
}
function getTasksForCurrent() {
    updatefilterFields();
    taskRequest =
        "id=" + taskId + "&" +
        "title=" + taskTitle + "&" +
        "status=" + taskStatus + "&" +
        "plannedStart=" + taskBegin + "&" +
        "plannedEnd=" + taskEnd + "&" +
        "tag=" + taskTag + "&" +
        "offset=" + offset + "&" +
        "limit=" + limit;

    getCountForCurrent();
    $.ajax({
        url: "/tasks/current?" + taskRequest,
        type: "GET",
        async: false,
        success: function (data) {
            tasks = data;
        },
        contentType: "application/json",
        dataType: 'json'
    });
}

function onFilter() {
    filter=true;
    redrawTasks();
}
function updatefilterFields(){
    if (filter) {
        taskId = $('#task_id').val();
        taskTitle = $('#task_title').val();
        taskStatus = $('#task_status option:selected').text();
        taskBegin = $('#task_begin').val();
        taskEnd = $('#task_end').val();
        taskTag = $('#task_tag').val();
    } else {
        taskId = '';
        taskTitle = '';
        taskStatus = '';
        taskBegin = '';
        taskEnd = '';
        taskTag = '';
    }
}
function offFilter() {
    filter=false;
    document.getElementById('task_id').value = '';
    document.getElementById('task_title').value = '';
    document.getElementById('task_status').value = '';
    document.getElementById('task_begin').value = '';
    document.getElementById('task_end').value = '';
    document.getElementById('task_tag').value = '';
    redrawTasks();
}
function redrawTasks() {
    getTasksForCurrent();
    drawTable();
}
function nextPage() {
    if (offset<limit) {
        offset=offset+limit;
        redrawTasks();
    }
}
function prevousPage(){
    if((offset-limit)>=0) {
        offset=offset-limit;
        redrawTasks();
    }
}
function updateStatus() {
    $.ajax({
        url: "/status",
        type: "GET",
        async: false,
        success: function (data) {
            statuslist = data.statuses;
        },
        contentType: "application/json",
        dataType: 'json'
    });
    for (let i = 0; i < statuslist.length; i++) {
            $("#task_status").append("<option value=\""+(i+1)+"\">"+ statuslist[i] +"</option>");
    }
}

function getCook(cookiename)
{
    var cookiestring=RegExp(cookiename+"=[^;]+").exec(document.cookie);
    return decodeURIComponent(!!cookiestring ? cookiestring.toString().replace(/^[^=]+./,"") : "");
}
function firstLoad() {
    updateStatus();
    redrawTasks();
    let userId=getCook('userId');
    $('#id_right').text(userId);
}