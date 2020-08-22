<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<h2>Редактирование кампании</h2>

<form:form commandName="announce" action="EditAnnounce.htm" method="post" name="announce">
  <form:hidden path="id"/>
  <div class="form-group row">
    <form:label path="text" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Текст</form:label>
    <div class="col-sm-5">
      <form:textarea path="text" rows="15" cssClass="form-control"/>
      <form:errors path="text" cssClass="text-danger" element="span"/>
    </div>
  </div>

  <div class="form-group row">
    <div class="col-sm-7">
      <a class="btn btn-outline-success" href="<c:url value="/home.htm"/>">Назад</a>
      <button type="submit" class="btn btn-primary">Сохранить</button>
    </div>
  </div>
</form:form>
