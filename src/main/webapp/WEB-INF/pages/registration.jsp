<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Регистрация</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.0.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/registration.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script>
        $(document).ready(function () {
            Registration.load('${pageContext.request.contextPath}');
        })
    </script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-6">
            <h2 class="">Регистрация нового пользователя</h2>
            <form class="form-horizontal" method="POST">
                <div class="form-group">
                    <label class="col-sm-4 control-label" for="login">Логин</label>
                    <div class="col-sm-8">
                        <input type="text" id="login" name="login" placeholder="" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-4 control-label" for="password">Пароль</label>
                    <div class="col-sm-8">
                        <input type="password" id="password" name="password" placeholder="" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-4 control-label" for="rep-password">Повторите пароль</label>
                    <div class="col-sm-8">
                        <input type="password" id="rep-password" name="rep-password" placeholder="" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-4 control-label" for="name">ФИО</label>
                    <div class="col-sm-8">
                        <input type="text" id="name" name="name" placeholder="" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-4 control-label" for="email">E-mail</label>
                    <div class="col-sm-8">
                        <input type="text" id="email" name="email" placeholder="" class="form-control">
                    </div>
                </div>
                <div class="control-group">
                    <div class="col-sm-offset-2 col-sm-8">
                        <button type="button" id="register" class="btn btn-success">Зарегистрировать</button>
                    </div>
                </div>
            </form>
        </div>
        <div class="col-md-3"></div>
    </div>
</div>
<div class="modal fade"  id="succesfull-dialog" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Регистрация</h4>
            </div>
            <div class="modal-body">
                <p>Регистрация прошла успешно!</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="go-main">Перейти на главную</button>
            </div>
        </div><!-- /.modal-content -->
    </div>
</div><!-- /.modal -->
<div class="modal fade"  id="errors-dialog" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Регистрация</h4>
            </div>
            <div class="modal-body">
                <b>На форме регистрации имеются ошибки:</b>
                <div id="errors-in-dialog">
                    <ul>

                    </ul>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="ok-btn">ОК</button>
            </div>
        </div><!-- /.modal-content -->
    </div>
</div><!-- /.modal -->

</body>
</html>
