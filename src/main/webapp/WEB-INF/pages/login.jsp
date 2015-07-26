<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Страница авторизации</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css"/>
</head>
<body>
<div class="container">
    <c:if test="${not empty sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}">
        <div class="row" style="padding-top: 20px;">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <div class="alert alert-danger">
                    Попытка входа была неудачна. Попробуйте еще раз.<br/>
                    Причина: <label id="error">${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</label>.<br>
                </div>
            </div>
            <div class="col-md-3"></div>
        </div>
    </c:if>

    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-6">
            <form class="form-horizontal" action="<c:url value='j_spring_security_check' />" method="POST">
                        <h2 class="">Введите логин и пароль</h2>

                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="j_username">Логин</label>

                        <div class="col-sm-10">
                            <input type="text" id="j_username" name="j_username" placeholder="" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="j_password">Пароль</label>

                        <div class="col-sm-10">
                            <input type="password" id="j_password" name="j_password" placeholder="" class="form-control">
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-success">Вход</button>
                            <a href="<c:url value='/registration'/>" class="btn btn-info" role="button">Регистрация</a>
                        </div>
                    </div>
            </form>
        </div>
        <div class="col-md-3"></div>
    </div>
</div>
</body>
</html>
