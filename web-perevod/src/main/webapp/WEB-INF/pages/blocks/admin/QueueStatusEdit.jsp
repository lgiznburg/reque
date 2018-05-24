<%--
  Created by IntelliJ IDEA.
  User: leonid
  Date: 23.12.17
  Time: 22:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h2>Статус в очереди</h2>
<form:form commandName="queueStatus" method="post">
  <form:hidden path="id"/>
  <div class="form-group row">
    <form:label path="name" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Название</form:label>
    <div class="col-sm-5">
      <form:input path="name"  cssClass="form-control"/>
      <form:errors path="name" cssClass="text-danger" element="span"/>
    </div>
  </div>
  <div class="form-group row">
    <form:label path="description" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Описание</form:label>
    <div class="col-sm-5">
      <form:input path="description"  cssClass="form-control"/>
      <form:errors path="description" cssClass="text-danger" element="span"/>
    </div>
  </div>
  <div class="form-group row">
    <form:label path="type" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Тип</form:label>
    <div class="col-sm-5">
      <form:select path="type" items="${queueStatusTypes}"/>
      <form:errors path="type" cssClass="text-danger" element="span"/>
    </div>
  </div>

  <div class="form-group row">
    <div class="col-sm-7">
      <a class="btn btn-outline-success" href="<c:url value="/admin/QueueStatuses.htm"/>">Назад</a>
      <button type="submit" class="btn btn-primary">Сохранить</button>
    </div>
  </div>

</form:form>