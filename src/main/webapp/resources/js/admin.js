var Admin = (function () {
    var contextPath;
    var userListTemplate;

    function load(contextPathNew) {
        contextPath = contextPathNew;
        userListTemplate = new t($('#user-list-template').html());
        refreshUsers();
        $('#add-btn').on('click', function() {
            showAddUserDialog();
        });
    }

    function showAddUserDialog() {
        var login = $('#login');
        var password = $('#password');
        var name = $('#name');
        var email = $('#email');
        var alert = $('#add-user-alert');
        alert.empty();
        alert.hide();
        login.val('');
        password.val('');
        name.val('');
        email.val('');
        $('#add-user-dialog').modal('show');
        $('#register-btn').off('click').on('click', function() {
            var hasError = false;
            hasError = isInvalidField(login) ? true : hasError;
            hasError = isInvalidField(password) ? true : hasError;
            hasError = isInvalidField(name) ? true : hasError;
            hasError = isInvalidField(email) ? true : hasError;
            if (hasError) {
                alert.show();
                alert.empty();
                alert.append('Не заполнены поля');
                return;
            }
            var data = {
                login:login.val(),
                password:password.val(),
                repPassword:password.val(),
                name:name.val(),
                email:email.val()
            };
            $.ajax({
                type: "POST",
                url: contextPath+ "/user/registerFromAdmin",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                async: false,
                data: JSON.stringify(data),
                success: function (resp) {
                    if (resp.success) {
                        refreshUsers();
                        $('#add-user-dialog').modal('hide');
                    }
                },
                error: function (textStatus, errorThrown) {
                    showExeption(textStatus);
                }
            });
        });
    }

    function isInvalidField(field) {
        if (!field.val()) {
            field.parent().parent().addClass('has-error');
            field.on('change', function () {
                field.parent().parent().removeClass('has-error');
                field.off('change');
            });
            return true;
        }
        return false;
    }

    function refreshUsers() {
        var users = $('#users').find('> tbody');
        users.empty();
        $.ajax({
            type: "GET",
            url: contextPath + "/admin/list",
            contentType: "application/json; charset=utf-8",
            async: false,
            success: function (resp) {
                if (resp.length > 0) {
                    var userText = renderTemplate(userListTemplate, {users: resp});
                    users.append(userText);
                }
            },
            error: function (jqXHR, textStatus, e) {
                showExeption(textStatus);
            }
        });
    }

    function makeNotAdmin(id) {
        $.ajax({
            type: "GET",
            url: contextPath + "/admin/makeNotAdmin/"+id,
            contentType: "application/json; charset=utf-8",
            async: false,
            success: function (resp) {
                if (resp.success) {
                    refreshUsers();
                }
            },
            error: function (jqXHR, textStatus, e) {
                showExeption(textStatus);
            }
        });
    }

    function makeAdmin(id) {
        $.ajax({
            type: "GET",
            url: contextPath + "/admin/makeAdmin/"+id,
            contentType: "application/json; charset=utf-8",
            async: false,
            success: function (resp) {
                if (resp.success) {
                    refreshUsers();
                }
            },
            error: function (jqXHR, textStatus, e) {
                showExeption(textStatus);
            }
        });
    }

    function deleteUser(id) {
        $.ajax({
            type: "DELETE",
            url: contextPath + "/admin/user/delete/"+id,
            async: false,
            success: function () {
                refreshUsers();
            },
            error: function (jqXHR, textStatus, e) {
                showExeption(textStatus);
            }
        });
    }

    function showExeption(textStatus) {
        var resp;
        try {
            resp = JSON.parse(textStatus.responseText);
            alert(resp.error);
        } catch (e) {
            alert("Произошла неизвестная ошибка на сервере");
        }
    }

    function renderTemplate(template, data) {
        return template.render(data);
    }


    return {
        load: load,
        deleteUser: deleteUser,
        makeAdmin: makeAdmin,
        makeNotAdmin: makeNotAdmin
    };
})();
