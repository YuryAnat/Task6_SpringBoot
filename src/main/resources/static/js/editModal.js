$(document).on('show.bs.modal','#editUser', function (e) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/rest/admin/"+$(e.relatedTarget).data('id'),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (result) {
            var id = $(result.id);
            var login = $(result.login);
            var name = $(result.name);
            var email = $(result.email);

            $('#id').val(id);
            $('#login').val(login);
            $('#name').val(name);
            $('#email').val(email);
        }
    });




});