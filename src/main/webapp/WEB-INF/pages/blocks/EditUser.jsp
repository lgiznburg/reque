<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<h2>Персональные данные</h2>

<form:form commandName="userToReg" name="user" method="post" action="EditUser.htm"  cssClass="form-horizontal">
  <form:hidden path="id"/>

  <div class="control-group">
    <form:label path="firstName" cssClass="control-label" cssErrorClass="control-label text-error">Имя <sup>*</sup></form:label>
    <div class="controls">
      <form:input path="firstName" />
      <form:errors path="firstName" element="span" cssClass="text-error" />
    </div>
  </div>
  <div class="control-group">
    <form:label path="lastName" cssClass="control-label" cssErrorClass="control-label text-error">Фамилия <sup>*</sup></form:label>
    <div class="controls">
      <form:input path="lastName" />
      <form:errors path="lastName" element="span" cssClass="text-error" />
    </div>
  </div>
  <div class="control-group">
    <form:label path="phoneNumber" cssClass="control-label" cssErrorClass="control-label text-error">Мобильный телефон </form:label>
    <div class="controls">
      <form:input path="phoneNumber" />
      <form:errors path="phoneNumber" element="span" cssClass="text-error" />
    </div>
  </div>

  <div class="control-group">
    <div class="controls">
      <a class="btn" href="<c:url value="/home.htm"/>">Назад</a>
      <button type="submit" class="btn btn-primary">Сохранить</button>
    </div>
  </div>
</form:form>
