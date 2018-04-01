<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<h2>Персональные данные</h2>

<form:form commandName="userToReg" name="user" method="post" action="EditUser.htm" >
  <form:hidden path="id"/>

  <div class="form-group row">
    <form:label path="firstName" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Имя <sup>*</sup></form:label>
    <div class="col-sm-5">
      <form:input path="firstName"   cssClass="form-control"/>
      <form:errors path="firstName" element="span" cssClass="text-danger" />
    </div>
  </div>
  <div class="form-group row">
    <form:label path="lastName" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Фамилия <sup>*</sup></form:label>
    <div class="col-sm-5">
      <form:input path="lastName"   cssClass="form-control"/>
      <form:errors path="lastName" element="span" cssClass="text-danger" />
    </div>
  </div>
  <div class="form-group row">
    <form:label path="phoneNumber" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Мобильный телефон </form:label>
    <div class="col-sm-5">
      <form:input path="phoneNumber"  cssClass="form-control" />
      <form:errors path="phoneNumber" element="span" cssClass="text-danger" />
    </div>
  </div>

  <div class="form-group row">
    <div class="col-sm-5">
      <a class="btn btn-outline-success" href="<c:url value="/home.htm"/>">Назад</a>
      <button type="submit" class="btn btn-primary">Сохранить</button>
    </div>
  </div>
</form:form>
