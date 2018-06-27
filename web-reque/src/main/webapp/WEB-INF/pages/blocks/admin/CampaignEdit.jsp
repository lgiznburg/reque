<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script>

  $( function(){
    var from =$("#startDate").datepicker()
            .on("change", function() {
              to.datepicker( "option", "minDate", getDate( this ) );
            }),
        to = $("#endDate").datepicker()
            .on("change", function() {
              from.datepicker( "option", "maxDate", getDate( this ) );
            });
    $("#startDate").datepicker("option", $.datepicker.regional[ "ru" ]);
    $("#endDate").datepicker("option", $.datepicker.regional[ "ru" ]);

    var dateFormat = "dd.mm.yy";
    function getDate( element ) {
      var date;
      try {
        date = $.datepicker.parseDate( dateFormat, element.value );
      } catch( error ) {
        date = null;
      }

      return date;
    }

  });
</script>

<h2>Редактирование кампании</h2>

<form:form commandName="campaign" action="CampaignEdit.htm" method="post" name="campaign">
  <form:hidden path="id"/>
  <div class="form-group row">
    <form:label path="name" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Название</form:label>
    <div class="col-sm-5">
      <form:input path="name"  cssClass="form-control"/>
      <form:errors path="name" cssClass="text-danger" element="span"/>
    </div>
  </div>
  <div class="form-group row">
    <form:label path="startDate" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Дата начала приема</form:label>
    <div class="col-sm-5">
      <form:input path="startDate"   cssClass="form-control"/>
      <form:errors path="startDate" cssClass="text-danger" element="span"/>
    </div>
  </div>
  <div class="form-group row">
    <form:label path="endDate" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Дата окончания приема</form:label>
    <div class="col-sm-5">
      <form:input path="endDate"  cssClass="form-control"/>
      <form:errors path="endDate" cssClass="text-danger" element="span"/>
    </div>
  </div>
  <div class="form-group row">
    <form:label path="availableTypes" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Возможные типы обращений</form:label>
    <div class="col-sm-5">
      <form:checkboxes items="${applianceTypes}" itemLabel="name" itemValue="id" path="availableTypes"  cssClass="form-check-input" element="div"/>
      <form:errors path="availableTypes" cssClass="text-danger" element="span"/>
    </div>
  </div>
  <div class="form-group row">
    <form:label path="active" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Активный</form:label>
    <div class="col-sm-5">
      <form:checkbox path="active"  cssClass="form-check-input" />
      <form:errors path="active" cssClass="text-danger" element="span"/>
    </div>
  </div>
  <div class="form-group row">
    <form:label path="priority" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Приоритет</form:label>
    <div class="col-sm-5">
      <form:input path="priority"  cssClass="form-control"/>
      <form:errors path="priority" cssClass="text-danger" element="span"/>
    </div>
  </div>
  <div class="form-group row">
    <form:label path="concurrentAmount" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Количество принимаемых в конкурентный период</form:label>
    <div class="col-sm-5">
      <form:input path="concurrentAmount"  cssClass="form-control"/>
      <form:errors path="concurrentAmount" cssClass="text-danger" element="span"/>
    </div>
  </div>

  <div class="form-group row">
    <div class="col-sm-7">
      <a class="btn btn-outline-success" href="<c:url value="/admin/StoredProperties.htm"/>">Назад</a>
      <button type="submit" class="btn btn-primary">Сохранить</button>
    </div>
  </div>
</form:form>

