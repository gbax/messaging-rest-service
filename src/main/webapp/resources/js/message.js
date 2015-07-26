var Messages = (function () {
    var contextPath;
    var userListTemplate;
    var messageRowTemplate;
    var adminMessageRowTemplate;
    var chooseUserTemplate;
    var userList;
    var userListDropDown;
    var addBtn;
    var tableBody;
    var order = "";
    var sortField = "";
    var messageDialog;

    function load(contextPathNew) {
        contextPath = contextPathNew;
        userListTemplate = new t($('#user-list-template').html());
        messageRowTemplate = new t($('#message-row-template').html());
        adminMessageRowTemplate = new t($('#admin-message-row-template').html());
        chooseUserTemplate = new t($('#choose-user-template').html());
        userList = $('#user-list');
        tableBody = $('#messages > tbody');
        userListDropDown = $('#user-list-drop');
        messageDialog = $('#message-dialog');
        addBtn = $('#add-btn');
        addBtn.on('click', function () {
            refreshUsersNotInList();
            $('#add-user-dialog').modal('show');
        });
        refreshUserList();
        refreshMessages();
    }

    function chooseUser(id) {
        $.ajax({
            type: "GET",
            url: contextPath + "/user/addNew/" + id,
            contentType: "application/json; charset=utf-8",
            async: false,
            success: function () {
                refreshUserList();
                $('#add-user-dialog').modal('hide');
            },
            error: function (jqXHR, textStatus, e) {
                showExeption(textStatus);
            }
        });
    }

    function deleteUser(id) {
        $.ajax({
            type: "DELETE",
            url: contextPath + "/user/deleteFromList/" + id,
            async: false,
            success: function () {
                refreshUserList();
            },
            error: function (jqXHR, textStatus, e) {
                showExeption(textStatus);
            }
        });
    }

    function refreshMessages() {
        tableBody.empty();
        var sortPar = {sort: sortField, order: order};
        $.ajax({
            type: "POST",
            url: contextPath + "/messages/list",
            contentType: "application/json; charset=utf-8",
            async: false,
            dataType: "json",
            data: JSON.stringify(sortPar),
            success: function (resp) {
                if (resp.messageModels.length > 0) {
                    var messagesText = "";
                    if (resp.isAdmin) {
                        messagesText += renderTemplate(adminMessageRowTemplate, {messages: resp.messageModels});
                    } else {
                        messagesText += renderTemplate(messageRowTemplate, {messages: resp.messageModels});
                    }

                    tableBody.append(messagesText);
                }
            },
            error: function (jqXHR, textStatus, e) {
                showExeption(textStatus);
            }
        });
    }

    function deleteMessage(id) {
        $.ajax({
            type: "DELETE",
            url: contextPath + "/messages/delete/" + id,
            async: false,
            success: function () {
                refreshMessages();
            },
            error: function (jqXHR, textStatus, e) {
                showExeption(textStatus);
            }
        });
    }

    function sort(sortValue, orderValue) {
        sortField = sortValue;
        order = orderValue;
        refreshMessages()
    }

    function refreshUserList() {
        userList.empty();
        $.ajax({
            type: "GET",
            url: contextPath + "/user/getUsers",
            contentType: "application/json; charset=utf-8",
            async: false,
            dataType: "json",
            success: function (resp) {
                if (resp.length > 0) {
                    var usersText = "";
                    resp.forEach(function (u) {
                        usersText += renderTemplate(userListTemplate, u);
                    });
                    userList.append(usersText);
                }
            },
            error: function (jqXHR, textStatus, e) {
                showExeption(textStatus);
            }
        });
    }

    function openMessage(id) {
        var mesFrom = $('#message-from');
        var mesDate = $('#message-date');
        var mesSubj = $('#message-subject');
        var mesBody = $('#message-body');
        mesBody.empty();
        mesDate.empty();
        mesSubj.empty();
        mesFrom.empty();
        $.ajax({
            type: "GET",
            url: contextPath + "/messages/" + id,
            contentType: "application/json; charset=utf-8",
            async: false,
            dataType: "json",
            success: function (resp) {
                mesFrom.append(resp.from);
                mesDate.append(resp.date);
                mesSubj.append(resp.subject);
                mesBody.append(resp.body);
                messageDialog.modal('show');
            },
            error: function (jqXHR, textStatus, e) {
                showExeption(textStatus);
            }
        });
    }

    function openSendMessage(id, name) {
        var sendMesDialog = $('#send-message-dialog');
        var messTo = $('#message-to');
        var mesSubj = $('#message-subject-text');
        var mesBody = $('#message-body-text');
        var sendMessBtn = $('#send-message-btn');
        messTo.empty();
        messTo.append(name);
        mesSubj.val('');
        mesBody.val('');
        sendMesDialog.modal('show');

        sendMessBtn.on('click', function () {
            var hasError = false;
            if (!mesSubj.val()) {
                hasError = true;
                mesSubj.parent().parent().addClass('has-error');
                mesSubj.on('change', function () {
                    mesSubj.parent().parent().removeClass('has-error');
                    mesSubj.off('change');
                });
            }
            if (!mesBody.val()) {
                hasError = true;
                mesBody.parent().parent().addClass('has-error');
                mesBody.on('change', function () {
                    mesBody.parent().parent().removeClass('has-error');
                    mesBody.off('change');
                });
            }
            if (hasError) {
                return;
            }
            var message = {
                toId: id,
                subject: mesSubj.val(),
                body: mesBody.val()
            };
            $.ajax({
                type: "POST",
                url: contextPath + "/messages/send",
                contentType: "application/json; charset=utf-8",
                async: false,
                data: JSON.stringify(message),
                success: function () {
                    sendMesDialog.modal('hide');
                },
                error: function (jqXHR, textStatus, e) {
                    showExeption(textStatus);
                }
            });
        });
    }

    function changePassword() {
        var newPass = $('#new-password');
        var pass = $('#password');
        var changePassAlert = $('#change-pass-alert');
        var changeModal = $('#change-password-dialog');
        var changePassForm = $('#change-pass-form');
        newPass.val('');
        pass.val('');
        changePassAlert.hide();
        changePassAlert.empty();
        changePassForm.removeClass('has-error');
        changeModal.modal('show');
        $('#ok-btn-change-password').on('click', function() {
            var hasError = false;
            if (!pass.val()) {
                hasError = true;
                pass.parent().parent().addClass('has-error');
                pass.on('change', function () {
                    pass.parent().parent().removeClass('has-error');
                    pass.off('change');
                });
            }
            if (!newPass.val()) {
                hasError = true;
                newPass.parent().parent().addClass('has-error');
                newPass.on('change', function () {
                    newPass.parent().parent().removeClass('has-error');
                    newPass.off('change');
                });
            }
            if (hasError) {
                return;
            }
            var data = {
                password: pass.val(),
                newPassword: newPass.val()
            };
            $.ajax({
                type: "POST",
                url: contextPath + "/user/changePassword",
                contentType: "application/json; charset=utf-8",
                async: false,
                dataType: "json",
                data: JSON.stringify(data),
                success: function (resp) {
                    if (!resp.success) {
                        changePassForm.addClass('has-error');
                        changePassAlert.empty();
                        changePassAlert.append(resp.message);
                        changePassAlert.show();
                    } else {
                        changeModal.modal('hide');
                    }
                },
                error: function (jqXHR, textStatus, e) {
                    showExeption(textStatus);
                }
            });
        });

    }

    function refreshUsersNotInList() {
        userListDropDown.empty();
        $.ajax({
            type: "GET",
            url: contextPath + "/user/getUsersNotInList",
            contentType: "application/json; charset=utf-8",
            async: false,
            dataType: "json",
            success: function (resp) {
                if (resp.length > 0) {
                    var usersText = "";
                    resp.forEach(function (u) {
                        usersText += renderTemplate(chooseUserTemplate, u);
                    });
                    userListDropDown.append(usersText);
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
        } catch (e) {
            alert("Произошла неизвестная ошибка на сервере");
        }
    }

    function renderTemplate(template, data) {
        return template.render(data);
    }


    return {
        load: load,
        chooseUser: chooseUser,
        deleteUser: deleteUser,
        sort: sort,
        deleteMessage: deleteMessage,
        openMessage: openMessage,
        openSendMessage: openSendMessage,
        changePassword: changePassword
    };
})();
