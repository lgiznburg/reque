<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<h2>Тип заявления</h2>

<form:form commandName="applianceType" action="ApplianceTypeEdit.htm" method="post" name="applianceType">
  <form:hidden path="id"/>

  <div class="form-group row">
    <form:label path="name" for="name" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Название</form:label>
    <div class="col-sm-5">
      <form:input path="name"  cssClass="form-control"/>
      <form:errors path="name"  cssClass="text-danger" element="span"/>
    </div>
  </div>
  <div class="form-group row">
    <form:label path="description" for="name" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Полное название</form:label>
    <div class="col-sm-5">
      <form:input path="description"  cssClass="form-control"/>
      <form:errors path="description" cssClass="text-danger" element="span"/>
    </div>
  </div>

  <div class="form-group row">
    <div class="col-sm-5">
      <a class="btn btn-outline-success" href="<c:url value="/admin/ApplianceTypes.htm"/>">Назад</a>
      <button class="btn btn-primary" type="submit">Сохранить</button>
    </div>
  </div>
</form:form>

