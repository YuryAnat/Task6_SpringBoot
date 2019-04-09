$(document).ready(function () {
    addAllUser();
});

$(document).on('show.bs.modal', '#editUser', function (e) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/rest/admin/" + $(e.relatedTarget).data('id'),
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

function addAllUser() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        url: "/rest/admin/",
        cache: false,
        timeout: 60000,
        success: function (data) {
            $("#user-table tr").remove();
            var table = "";
            $.each(data, function (key, value) {
                table += "<tr>";
                table += '<td>' + value.id + '</td>';
                table += "<td>" + value.login + "</td>";
                table += "<td>" + value.name + "</td>";
                table += "<td>" + value.email + "</td>";
                table += "<td>";
                $.each(value.roles, function (index, value) {
                    table += "<span>" + value.role + " " + "</span>"
                });
                table += "</td>";
                table += "<td>" + "<button class='btn-info btn btn-sm' type='button' data-toggle='modal' data-target='#editUser' data-id=" + value.id + ">Edit</button>" + "</td>";
                table += "<td>" +
                    "<input type='button' value='Delete' id='" + value.id + "' class='delete-user btn btn-danger btn-sm'>"
                    + "</td>";
                table += "</tr>";
            });
            $("#user-table").append(table)
        }
    })
}

$(document).on('click', '.delete-user', function(){
    var id = $(this).attr("id");
    $.ajax({
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        url: "/rest/admin/delete/",
        data: JSON.stringify({"id": id}),
        success: function () {
            addAllUser();
        }
    })
});

$(document).on("click",".edit-button", function () {
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
        success: function () {
            addAllUser();
            $("#editUser .b-close").click();
        }
    })
});

$(document).on("click",".add-user", function () {
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
        success: function () {
            addAllUser();
            $("#listUser").tab("show");
        }
    })
});