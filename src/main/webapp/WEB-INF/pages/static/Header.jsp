<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div style="width: 100%">
  <div style="float: right;">
    <sec:authorize access="isAnonymous()">
      <a href="<c:url value="/login.htm"/>">Войти</a> <br/>
      <a href="<c:url value="/Registration.htm"/>">Зарегистрироваться</a>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
      <p><strong>${user.firstName} ${user.lastName}</strong></p>
      <p>
      <form action="<c:url value="/logout"/>" method="post" id="logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <a href="<c:url value="/logout"/>" onclick="$('#logout').submit();return false;">Выйти</a>
      </form>
      </p>
    </sec:authorize>
  </div>

  <div><h2>РНИМУ им. Пирогова</h2></div>
</div>