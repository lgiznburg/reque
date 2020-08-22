<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
  <title>Напомнить пароль</title>

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


  <form name='remindPassword'
        action="<c:url value='/RemindPassword.htm' />" method='POST' class="form-signin">

    <h4 class="form-signin-heading"><spring:message code="remind.enter_password"/></h4>

    <c:if test="${not empty userNotFound}">
      <div class="form-text text-danger">
        <c:url value="/Registration.htm" var="reg_link"/>
        <c:set var="reg_url"><a href="${reg_link}"><spring:message code="basic.register"/></a></c:set>
        <spring:message code="remind.email_not_found" arguments="${reg_url}" htmlEscape="false"/>
          <%--К сожалению, указанный email не найден. Вы можете <a href="<c:url value="/Registration.htm"/>">зарегистрироваться</a> на на нашем сервисе.--%>
      </div>
    </c:if>
    <c:if test="${not empty success}">
      <div class="form-text text-success"><spring:message code="remind.password_sent"/></div>
      <div class="form-text text-success"><spring:message code="remind.thank_you"/> </div>
    </c:if>

    <input type='text' name='email' value='' class="form-control" placeholder="Email">
    <button class="btn btn-primary" type="submit"><spring:message code="remind.send_reminder"/></button>

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