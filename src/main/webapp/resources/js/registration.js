var Registration = (function () {

    var login;
    var password;
    var repPassword;
    var name;
    var email;
    var button;
    var contextPath;

    function load(contextPathNew) {
        $('#go-main').on('click', function () {
            document.location.href = contextPath+'/';
        });
        $('#ok-btn').on('click', function () {
            $('#errors-dialog').modal('hide');
        });
        contextPath = contextPathNew;
        login = $('#login');
        password = $('#password');
        repPassword = $('#rep-password');
        name = $('#name');
        email = $('#email');
        button = $('#register');
        button.on('click', function () {
            register();
        })
    }

    function register() {
        if (hasErrors()) {
            return;
        }
        var data = getRegistarionData();
        $.ajax({
            type: "POST",
            url: contextPath+ "/user/register",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            async: false,
            data: JSON.stringify(data),
            success: function (resp) {
                if (resp.success) {
                    $('#succesfull-dialog').modal('show');
                }
            },
            error: function (textStatus, errorThrown) {
                showExeption(textStatus);
            }
        });
    }

    function getRegistarionData() {
        return {
            login: login.val(),
            password: password.val(),
            repPassword: repPassword.val(),
            name: name.val(),
            email: email.val()
        }
    }

    function hasErrors() {
        var errors = [];
        validateNotEmptyField(login, errors);
        validateNotEmptyField(password, errors);
        validateNotEmptyField(repPassword, errors);
        validateNotEmptyField(name, errors);
        validateNotEmptyField(email, errors);
        if (!!password.val() && !!repPassword.val()) {
            checkRepeatPassEq(errors);
        }
        if (!!login.val()) {
            checkDuplicateUser(errors, login.val());
        }
        if (errors.length > 0) {
            showError(errors);
            return true;
        }
        return false;
    }

    function validateNotEmptyField(field, errors) {
        var fieldVal = field.val();
        if (!fieldVal) {
            makeErrorField(field);
            var fieldContent = $('label[for="' + field.attr('id') + '"]').text();
            errors.push('Не заполнено поле "' + fieldContent + '"');
        }
    }

    function makeErrorField(field) {
        field.parent().parent().addClass('has-error');
        field.on('change', function () {
            field.parent().parent().removeClass('has-error');
            field.off('change');
        });
    }

    function checkRepeatPassEq(errors) {
        if (password.val() !== repPassword.val()) {
            makeErrorField(repPassword);
            errors.push('Пароли не совпадают');
        }
    }

    function checkDuplicateUser(errors) {
        var userLogin = login.val();
        $.ajax({
            type: "GET",
            url: contextPath+"/user/checkDuplicate/" + userLogin,
            contentType: "application/json; charset=utf-8",
            async: false,
            dataType: "json",
            success: function (resp) {
                if (resp.success) {
                    errors.push("Пользователь с таким именем существует");
                    makeErrorField(login);
                }
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
        } catch(e) {
            alert("Произошла неизвестная ошибка на сервере");
        }
    }

    function showError(errors) {
        var errorsLi = '';
        errors.forEach(function(err) {
            errorsLi += '<li>'+err+'</li>'
        });
        $('#errors-in-dialog').find('> ul').empty();
        $('#errors-in-dialog').find('> ul').append(errorsLi);
        $('#errors-dialog').modal('show');
    }

    return {
        load: load
    };
})();

