$(document).on('show.bs.modal','#editUser', function (e) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/rest/admin/"+$(e.relatedTarget).data('id'),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            $('#id').val(data.id);
            $('#login').val(data.login);
            $('#name').val(data.name);
            $('#email').val(data.email);
        }
    });
});

function addForm(){
    var newUser = JSON.stringify({
        "login": $("#newLogin").val(),
        "password": $("#newPassword").val(),
        "name": $("#newName").val(),
        "email": $("#newEmail").val(),
        "roles": $(".roles:checked").map(function () {
            return $(this).val();
        }).get()
    });

    $.ajax({
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        url: "/rest/admin/add/",
        data: newUser,
        success: function () {top.location.href="/admin"}
    })
}

function editForm(){
    var editUser = JSON.stringify({
        "id": $("#id").val(),
        "login": $("#login").val(),
        "password": $("#password").val(),
        "name": $("#name").val(),
        "email": $("#email").val(),
        "roles": $(".editRoles:checked").map(function () {
            return $(this).val();
        }).get()
    });

    $.ajax({
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        url: "/rest/admin/edit/",
        data: editUser,
        success: function () {top.location.href="/admin"}
    })
}

function deleteForm(){
    var id = $("#deleteId").attr("data-id");
    $.ajax({
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        url: "/rest/admin/delete/"+id,
        success: function () {}
})
}

$(document).ready(function () {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        url: "/rest/admin/",
        cache: false,
        timeout: 60000,
        success: function (data) {
            var table = "";
            $.each(data,function (key, value) {
                table += "<tr>";
                table += '<td>' + value.id + '</td>';
                table += "<td>" + value.login + "</td>";
                table += "<td>" + value.name + "</td>";
                table += "<td>" + value.email + "</td>";
                table += "<td>"
                    $.each(value.roles, function (index, value) {
                        table += "<span>" + value.role + " " + "</span>"
                    });
                table += "</td>";
                table += "<td>" + "<button class='btn-info btn btn-sm' type='button' data-toggle='modal' data-target='#editUser' data-id=" + value.id + ">Edit</button>" + "</td>";
                table += "<td>" +
                    // "<form id='deleteForm' action='/admin' method='get'> " +
                    //     "<button id='deleteId' onclick='deleteForm()' type='submit' class='btn btn-danger btn-sm' data-id="+ value.id +">Delete</button>" +
                    // "</form>"
                    "<a href= /rest/admin/delete/" + value.id + " class = 'btn btn-danger btn-sm'>Delete</a>"
                    + "</td>";
                table += "</tr>";
            });
            $("#user-table").append(table)
        }
    })
})