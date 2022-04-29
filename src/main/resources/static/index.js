function init() {
    $.ajax({
        url: "/getTwoToCompare", type: "GET", async: false, success: function (data) {
            if (data) {
                $('#compare').empty()
                    .append("<div class=\"col\">\n" + "      <button class=\"btn btn-outline-dark\" type=\"button\" id=\"taskA\">" + data[0].taskContent + "</button>\n" + "    </div>")
                    .append("<div class=\"col\">\n" + "      <button class=\"btn btn-outline-dark\" type=\"button\" id=\"taskB\">" + data[1].taskContent + "</button>\n" + "    </div>");
            }
        }
    });
    $.ajax({
        url: "/getOrderedTaskList", type: "GET", success: function (data) {
            if (data) {
                data.forEach(element => {
                    $("#ordered").append("<dt><button class=\"btn btn-outline-dark\" type=\"button\" id='kill' name='" + element.taskUuid + "'>X</button>" + element.taskContent + "</dt>")
                });
            }
        }
    });
    $.ajax({
        url: "/getBufferedTaskList", type: "GET", success: function (data) {
            if (data) {
                data.forEach(element => {
                    $("#buffered").append("<dt><button class=\"btn btn-outline-dark\" type=\"button\" id='kill' name='" + element.taskUuid + "'>X</button>" + element.taskContent + "</dt>")
                });
            }
        }
    });

}

$(document).ready(init());
$('#submitTask').click(function () {
    $.ajax({
        url: "/addTaskToBufferedList",
        type: "POST",
        async: false,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify($("#newTask").val())
    });
    window.location.reload();
});

$('#clear').click(function () {
    $.ajax({
        url: "/clear", type: "GET", async: false
    });
    window.location.reload();
});

$(document).on("click", "#kill", function () {
    $.ajax({
        url: "/killTask", type: "POST", async: false, data: {
            taskUuid: $(this).attr("name")
        }
    });
    window.location.reload();
})

$(document).on('click', '#taskA', function () {
    $.ajax({
        url: "/setCompare", type: "POST", async: false, data: {
            flag: 0
        }
    });
    window.location.reload();
});

$(document).on('click', '#taskB', function () {
    $.ajax({
        url: "/setCompare", type: "POST", async: false, data: {
            flag: 1
        }
    });
    window.location.reload();
});

document.addEventListener("keydown", function (event) {
    if (event.keyCode === 13) {
        $('#submitTask').click();
    }
    if (event.key === "ArrowLeft") {
        $('#taskA').click();
    }
    if (event.key === "ArrowRight") {
        $('#taskB').click();
    }
});
