<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Пользователи</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.0.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/admin.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/t.min.js"></script>
    <script>
        $(document).ready(function () {
            Admin.load('${pageContext.request.contextPath}');
        })
    </script>
    <script id="user-list-template" type="t/template">
        {{@users}}
        <tr>
            <td>{{=_val.name}}</td>
            <td>{{=_val.email}}</td>
            <td>{{=_val.login}}</td>
            <td>{{=_val.role}}</td>
            <td>
                <div class="btn-toolbar" role="toolbar">
                    <button type="button" class="btn btn-sm btn-primary"
                            onclick="Admin.deleteUser({{=_val.id}})">
                        <span class="glyphicon glyphicon-trash" aria-hidden="true"></span> Удалить
                    </button>
                    {{_val.admin}}
                    <button type="button" class="btn btn-sm btn-primary"
                            onclick="Admin.makeNotAdmin({{=_val.id}})">
                        <span class="glyphicon glyphicon-circle-arrow-down" aria-hidden="true"></span> Сделать ROLE_USER
                    </button>
                    {{:_val.admin}}
                    <button type="button" class="btn btn-sm btn-primary"
                            onclick="Admin.makeAdmin({{=_val.id}})">
                        <span class="glyphicon glyphicon-circle-arrow-up" aria-hidden="true"></span> Сделать ROLE_ADMIN
                    </button>
                    {{/_val.admin}}
                </div>
            </td>
        </tr>
        {{/@users}}
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
            <a class="navbar-brand" href="#">Список пользователей</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="<c:url value="/" />">Главная</a></li>
                <li><a href="<c:url value="j_spring_security_logout" />">Выйти</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <div class="row">
        <div class="row">
            <div class="btn-toolbar" role="toolbar">
                <button type="button" class="btn btn-lg btn-primary" id="add-btn">
                    <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span> Добавить
                </button>
            </div>
        </div>
        <table class="table" id="users" style="margin-top: 15px;">
            <thead>
            <th>ФИО</th>
            <th>Email</th>
            <th>Логин</th>
            <th>Роль</th>
            <th>Операции</th>
            </thead>
            <tbody>

            </tbody>
        </table>
    </div>
</div>


<div class="modal fade" id="add-user-dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="message-dialog-title">Регистрация нового пользователя</h4>
            </div>
            <div class="modal-body">
                <div class="alert alert-danger" role="alert" id="add-user-alert"></div>
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="login">Логин</label>

                        <div class="col-sm-8">
                            <input type="text" id="login" placeholder="" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="password">Пароль</label>

                        <div class="col-sm-8">
                            <input type="password" id="password" placeholder="" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="name">ФИО</label>

                        <div class="col-sm-8">
                            <input type="text" id="name" placeholder="" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="email">E-mail</label>

                        <div class="col-sm-8">
                            <input type="text" id="email" placeholder="" class="form-control">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                <button type="button" class="btn btn-primary" id="register-btn">Зарегистрировать</button>
            </div>
        </div>
    </div>
</div>


</body>
</html>
