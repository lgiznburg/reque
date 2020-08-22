<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
  <title><spring:message code="login.title"/> </title>

  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <link rel="stylesheet" href="<c:url value="/resources/css/jquery-ui.min.css"/>"/>
  <!-- Bootstrap -->
  <link href="<c:url value="/resources/css/bootstrap.css"/>" rel="stylesheet" media="screen">
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

  <script src="<c:url value="/resources/js/jquery.js"/>"></script>

</head>
<body onload='document.loginForm.j_username.focus();'>


<div class="container">


  <form name='loginForm'
        action="<c:url value='/j_spring_security_check' />" method='POST' class="form-signin">

    <h4 class="form-signin-heading"><spring:message code="login.greeting"/></h4>

    <c:if test="${not empty error}">
      <div class="form-text text-danger">${error}</div>
    </c:if>
    <c:if test="${not empty msg}">
      <div class="form-text text-info">${msg}</div>
    </c:if>

    <div class="form-group">
      <input type='text' id="j_username" name='j_username' value='' class="form-control" placeholder="Email address">
    </div>
    <div class="form-group">
      <input type='password' id="j_password" name='j_password'  class="form-control" placeholder="<spring:message code="edit_password.pasword"/>"/>
    </div>
    <div class="form-group">
      <button class="btn btn-primary" type="submit">Войти</button>
    </div>

    <div class="form-group"><a href="<c:url value="/RemindPassword.htm"/>"><spring:message code="login.foget_password"/> </a> </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

  </form>
</div>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="<c:url value="/resources/js/popper.min.js"/> "></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/> "></script>
</body>
</html>