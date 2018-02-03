<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%--
    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="brand" href="#">Project name</a>
          <div class="nav-collapse collapse">
            <ul class="nav">
              <li class="active"><a href="#">Home</a></li>
              <li><a href="#about">About</a></li>
              <li><a href="#contact">Contact</a></li>
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <b class="caret"></b></a>
                <ul class="dropdown-menu">
                  <li><a href="#">Action</a></li>
                  <li><a href="#">Another action</a></li>
                  <li><a href="#">Something else here</a></li>
                  <li class="divider"></li>
                  <li class="nav-header">Nav header</li>
                  <li><a href="#">Separated link</a></li>
                  <li><a href="#">One more separated link</a></li>
                </ul>
              </li>
            </ul>
            <form class="navbar-form pull-right">
              <input class="span2" type="text" placeholder="Email">
              <input class="span2" type="password" placeholder="Password">
              <button type="submit" class="btn">Sign in</button>
            </form>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>
--%>

<div class="navbar navbar-fixed-top" role="navigation">
  <div class="navbar-inner">
    <div class="container">
      <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".navbar-collapse">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="brand" href="<c:url value="/home.htm"/>">РНИМУ им. Пирогова</a>
      <sec:authorize access="isAnonymous()">
        <div class="nav-collapse collapse">
          <form:form action="j_spring_security_check" class="navbar-form pull-right">
              <input type="text" class="span2" placeholder="Email" name="j_username" >
              <input type="password" class="span2" placeholder="Пароль" name="j_password" >
              <button type="submit" class="btn">Войти</button>
          </form:form>
          <ul class="nav pull-right">
            <li><a href="<c:url value="/Registration.htm"/>">Зарегистрироваться</a></li>
          </ul>
        </div>
      </sec:authorize>
      <sec:authorize access="isAuthenticated()">
        <div class="nav-collapse collapse">
          <ul class="nav pull-right">
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Добро пожаловать, ${user.firstName} <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <sec:authorize access="hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')">
                  <c:choose>
                    <c:when test="${empty appointment}">
                      <li><a href="<c:url value="SelectCampaign.htm"/> ">Предварительная запись</a></li>
                    </c:when>
                    <c:otherwise>
                      <li><a href="<c:url value="/CreateAppointment.htm"><c:param name="id" value="${appointment.id}"/></c:url>">Изменить Вашу запись.</a> </li>
                    </c:otherwise>
                  </c:choose>
                </sec:authorize>
                <li class="divider"></li>
                <li><a onclick="$('#logout').submit();return false;">Выйти</a></li>
              </ul>
            </li>
          </ul>
        </div>
        <form action="<c:url value="/logout"/>" method="post" id="logout">
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        </form>

      </sec:authorize>
    </div>
  </div>
</div>
