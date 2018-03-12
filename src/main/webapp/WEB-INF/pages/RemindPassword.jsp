<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<html>
<head>
  <title>Login Page</title>

  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <link rel="stylesheet" href="<c:url value="/resources/css/jquery-ui.min.css"/>"/>
  <!-- Bootstrap -->
  <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet" media="screen">
  <style type="text/css">
    body {
      padding-top: 40px;
      padding-bottom: 40px;
      background-color: #f5f5f5;
    }

    .form-signin {
      max-width: 300px;
      padding: 19px 29px 29px;
      margin: 0 auto 20px;
      background-color: #fff;
      border: 1px solid #e5e5e5;
      -webkit-border-radius: 5px;
      -moz-border-radius: 5px;
      border-radius: 5px;
      -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
      -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
      box-shadow: 0 1px 2px rgba(0,0,0,.05);
    }
    .form-signin .form-signin-heading,
    .form-signin .checkbox {
      margin-bottom: 10px;
    }
    .form-signin input[type="text"],
    .form-signin input[type="password"] {
      font-size: 16px;
      height: auto;
      margin-bottom: 15px;
      padding: 7px 9px;
    }

  </style>
  <link href="<c:url value="/resources/css/bootstrap-responsive.min.css"/>" rel="stylesheet">

  <script src="<c:url value="/resources/js/jquery.js"/>"></script>

</head>
<body onload='document.loginForm.j_username.focus();'>


<div class="container">


  <form name='remindPassword'
        action="<c:url value='/RemindPassword.htm' />" method='POST' class="form-signin">

    <h2 class="form-signin-heading">Пожалуйста, укажите Ваш email</h2>

    <c:if test="${not empty userNotFound}">
      <div class="error">К сожалению, указанный email не найден. Вы можете <a href="<c:url value="/Registration.htm"/>">зарегистрироваться</a> на на нашем сервисе.</div>
    </c:if>
    <c:if test="${not empty success}">
      <div class="msg">Письмо с иструкциями по обновлению пароля направлено на Вашу почту.
        Воспользуйтесь им в ближайшее время.</div>
      <div class="msg">Спасибо за использование нашего сервиса.</div>
    </c:if>

    <input type='text' name='email' value='' class="input-block-level" placeholder="Email address">
    <button class="btn btn-primary" type="submit">Напомнить пароль</button>

    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

  </form>
</div>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="<c:url value="/resources/js/bootstrap.min.js"/> "></script>
</body>
</html>