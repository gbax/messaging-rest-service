<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Сообщения</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.0.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/message.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/t.min.js"></script>
    <script>
        $(document).ready(function () {
            Messages.load('${pageContext.request.contextPath}');
        })
    </script>
    <script type="t/template" id="user-list-template">
        <li class="list-group-item">
            {{=name}}
            <button type="button" class="btn btn-sm btn-primary pull-right" id="ok-delete"
                    onclick="Messages.deleteUser({{=id}})">
                <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
            </button>
            <button type="button" class="btn btn-sm btn-primary pull-right" id="ok-send"
                    onclick="Messages.openSendMessage({{=id}}, '{{=name}}')">
                <span class="glyphicon glyphicon-comment" aria-hidden="true"></span>
            </button>
        </li>
    </script>
    <script id="choose-user-template" type="t/template">
        <li><a href="#" onclick="Messages.chooseUser({{=id}})">{{=name}}</a></li>
    </script>
    <script id="message-row-template" type="t/template">
        {{@messages}}
        <tr>
            <td>{{=_val.from}}</td>
            <td>{{=_val.date}}</td>
            <td><a href="#" onclick="Messages.openMessage({{=_val.id}})">{{=_val.subject}}</a></td>
            <td>
                <button type="button" class="btn btn-sm btn-primary pull-right"
                        onclick="Messages.deleteMessage({{=_val.id}})">
                    <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                </button>
            </td>
        </tr>
        {{/@messages}}
    </script>
    <script id="admin-message-row-template" type="t/template">
        {{@messages}}
        <tr>
            <td>{{=_val.from}}</td>
            <td>{{=_val.to}}</td>
            <td>{{=_val.date}}</td>
            <td><a href="#" onclick="Messages.openMessage({{=_val.id}})">{{=_val.subject}}</a></td>
            <td>
                <button type="button" class="btn btn-sm btn-primary pull-right"
                        onclick="Messages.deleteMessage({{=_val.id}})">
                    <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                </button>
            </td>
        </tr>
        {{/@messages}}
    </script>
</head>
<body style="padding-top: 70px;">
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Список сообщений</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="#" onclick="Messages.changePassword()">Сменить пароль</a></li>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <li><a href="<c:url value="/admin"/>">Управление пользователя</a></li>
                </sec:authorize>
                <li><a href="<c:url value="j_spring_security_logout" />">Выйти</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <div class="row">
        <div class="col-md-3">
            <div class="btn-toolbar" role="toolbar">
                <button type="button" class="btn btn-lg btn-primary" id="add-btn">
                    <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span> Добавить
                </button>
            </div>
            <ul class="list-group" style="margin-top: 20px;" id="user-list">

            </ul>
        </div>
        <div class="col-md-9">
            <table class="table" id="messages">
                <thead>
                <th>
                    От кого
                    <button class="btn btn-default btn-xs pull-right" type="button"
                            onclick="Messages.sort('author', 'asc')">
                        <span class="glyphicon glyphicon-arrow-up" aria-hidden="true"></span>
                    </button>
                    <button class="btn btn-default btn-xs pull-right" type="button"
                            onclick="Messages.sort('author', 'desc')">
                        <span class="glyphicon glyphicon-arrow-down" aria-hidden="true"></span>
                    </button>
                </th>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <th>Кому
                        <button class="btn btn-default btn-xs pull-right" type="button"
                                onclick="Messages.sort('toUser', 'asc')">
                            <span class="glyphicon glyphicon-arrow-up" aria-hidden="true"></span>
                        </button>
                        <button class="btn btn-default btn-xs pull-right" type="button"
                                onclick="Messages.sort('toUser', 'desc')">
                            <span class="glyphicon glyphicon-arrow-down" aria-hidden="true"></span>
                        </button>
                    </th>
                </sec:authorize>
                <th>Дата
                    <button class="btn btn-default btn-xs pull-right" type="button"
                            onclick="Messages.sort('createDate', 'asc')">
                        <span class="glyphicon glyphicon-arrow-up" aria-hidden="true"></span>
                    </button>
                    <button class="btn btn-default btn-xs pull-right" type="button"
                            onclick="Messages.sort('createDate', 'desc')">
                        <span class="glyphicon glyphicon-arrow-down" aria-hidden="true"></span>
                    </button>
                </th>
                <th>Тема
                    <button class="btn btn-default btn-xs pull-right" type="button"
                            onclick="Messages.sort('subject', 'asc')">
                        <span class="glyphicon glyphicon-arrow-up" aria-hidden="true"></span>
                    </button>
                    <button class="btn btn-default btn-xs pull-right" type="button"
                            onclick="Messages.sort('subject', 'desc')">
                        <span class="glyphicon glyphicon-arrow-down" aria-hidden="true"></span>
                    </button>
                </th>
                <th>Удалить</th>
                </thead>
                <tbody>

                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="modal fade" id="change-password-dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Смена пароля</h4>
            </div>
            <div class="modal-body">
                <div class="alert alert-danger" role="alert" id="change-pass-alert"></div>
                <form class="form-horizontal" id="change-pass-form">
                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="password">Текущий пароль</label>

                        <div class="col-sm-8">
                            <input type="password" id="password" name="password" placeholder="" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="new-password">Новый пароль</label>

                        <div class="col-sm-8">
                            <input type="password" id="new-password" name="new-password" placeholder=""
                                   class="form-control">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="ok-btn-change-password">ОК</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="message-dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="message-dialog-title">Сообщение</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">От кого</label>

                        <div class="col-sm-10">
                            <p class="form-control-static" id="message-from"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">Дата</label>

                        <div class="col-sm-10">
                            <p class="form-control-static" id="message-date"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">Тема</label>

                        <div class="col-sm-10">
                            <p class="form-control-static" id="message-subject"></p>
                        </div>
                    </div>
                    <blockquote>
                        <p id="message-body"></p>
                    </blockquote>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="add-user-dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Добавление пользователя</h4>
            </div>
            <div class="modal-body">
                <div class="dropdown">
                    <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                        Выберите пользователя
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" aria-labelledby="dropdownMenu1" id="user-list-drop">

                    </ul>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="send-message-dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Новое сообщение</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">Кому</label>

                        <div class="col-sm-10">
                            <p class="form-control-static" id="message-to"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="message-subject-text" class="col-sm-2 control-label">Тема</label>

                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="message-subject-text">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="message-subject-text" class="col-sm-2 control-label">Сообщение</label>

                        <div class="col-sm-10">
                            <textarea class="form-control" rows="3" id="message-body-text"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                <button type="button" class="btn btn-primary" id="send-message-btn">Отправить</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
