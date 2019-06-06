<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script language="JavaScript">
  $( function() {
    $("#birthDate").datepicker( {
      showOptions: { direction: "down" },
      showAnim: "slideDown"
    } );
  });
  $( function() {
    $("#sessionEndDate").datepicker( {
      showOptions: { direction: "down" },
      showAnim: "slideDown"
    } );
  });

</script>


<h2>Персональные данные</h2>

<form:form commandName="userToReg" name="user" method="post" action="EditUser.htm" >
  <form:hidden path="id"/>

  <div class="form-group row">
    <form:label path="firstName" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Имя <sup>*</sup></form:label>
    <div class="col-sm-5">
      <form:input path="firstName" class="form-control" />
      <form:errors path="firstName" element="span" cssClass="text-danger" />
    </div>
  </div>
  <div class="form-group row">
    <form:label path="additionalUserInfo.middleName" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Отчество <sup>*</sup></form:label>
    <div class="col-sm-5">
      <form:input path="additionalUserInfo.middleName" class="form-control" />
      <form:errors path="additionalUserInfo.middleName" element="span" cssClass="text-danger" />
    </div>
  </div>
  <div class="form-group row">
    <form:label path="lastName" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Фамилия <sup>*</sup></form:label>
    <div class="col-sm-5">
      <form:input path="lastName" class="form-control" />
      <form:errors path="lastName" element="span" cssClass="text-danger" />
    </div>
  </div>
  <div class="form-group row">
    <form:label path="additionalUserInfo.citizenship" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Гражданство <sup>*</sup></form:label>
    <div class="col-sm-5">
      <form:input path="additionalUserInfo.citizenship" class="form-control" />
      <form:errors path="additionalUserInfo.citizenship" element="span" cssClass="text-danger" />
    </div>
  </div>
  <div class="form-group row">
    <form:label path="additionalUserInfo.birthDate" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Дата рождения <sup>*</sup></form:label>
    <div class="col-sm-5">
      <form:input path="additionalUserInfo.birthDate" id="birthDate" class="form-control" />
      <form:errors path="additionalUserInfo.birthDate" element="span" cssClass="text-danger" />
    </div>
  </div>
  <div class="form-group row">
    <form:label path="additionalUserInfo.documentNumber" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Серия и номер документа удостоверяющего личность <sup>*</sup></form:label>
    <div class="col-sm-5">
      <form:input path="additionalUserInfo.documentNumber" class="form-control" />
      <form:errors path="additionalUserInfo.documentNumber" element="span" cssClass="text-danger" />
    </div>
  </div>
  <div class="form-group row">
    <form:label path="phoneNumber" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Мобильный телефон  <sup>*</sup></form:label>
    <div class="col-sm-5">
      <form:input path="phoneNumber" class="form-control" />
      <form:errors path="phoneNumber" element="span" cssClass="text-danger" />
    </div>
  </div>
  <div class="form-group row">
    <form:label path="additionalUserInfo.sessionEndDate" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Дата окончания сессии <sup>*</sup></form:label>
    <div class="col-sm-5">
      <form:input path="additionalUserInfo.sessionEndDate" id="sessionEndDate" class="form-control" />
      <form:errors path="additionalUserInfo.sessionEndDate" element="span" cssClass="text-danger" />
    </div>
  </div>
  <div class="form-group row">
    <form:label path="additionalUserInfo.representativeFullName" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">ФИО представителя</form:label>
    <div class="col-sm-5">
      <form:input path="additionalUserInfo.representativeFullName" class="form-control" />
      <form:errors path="additionalUserInfo.representativeFullName" element="span" cssClass="text-danger" />
    </div>
  </div>

  <div class="form-group row">
    <div class="col-sm-5">
      <a class="btn btn-outline-success" href="<c:url value="/home.htm"/>">Назад</a>
      <button type="submit" class="btn btn-primary">Сохранить</button>
    </div>
  </div>
</form:form>
