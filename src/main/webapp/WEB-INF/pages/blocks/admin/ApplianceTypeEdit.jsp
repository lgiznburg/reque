<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<h2>Тип заявления</h2>

<form:form commandName="applianceType" action="ApplianceTypeEdit.htm" method="post" name="applianceType" cssClass="form-horizontal">
  <form:hidden path="id"/>

  <div class="control-group">
    <form:label path="name" for="name" cssClass="control-label" cssErrorClass="control-label text-error">Название</form:label>
    <div class="controls">
      <form:input path="name"/>
      <form:errors path="name"  cssClass="text-error" element="span"/>
    </div>
  </div>
  <div class="control-group">
    <form:label path="description" for="name" cssClass="control-label" cssErrorClass="control-label text-error">Полное название</form:label>
    <div class="controls">
      <form:input path="description"/>
      <form:errors path="description" cssClass="text-error" element="span"/>
    </div>
  </div>

  <div class="control-group">
    <div class="controls">
      <a class="btn" href="<c:url value="/admin/ApplianceTypes.htm"/>">Назад</a>
      <button class="btn btn-primary" type="submit">Сохранить</button>
    </div>
  </div>
</form:form>

