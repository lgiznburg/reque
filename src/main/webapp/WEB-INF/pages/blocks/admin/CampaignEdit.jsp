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

<form:form commandName="campaign" action="CampaignEdit.htm" method="post" name="campaign" cssClass="form-horizontal">
  <form:hidden path="id"/>
  <div class="control-group">
    <form:label path="name" cssClass="control-label" cssErrorClass="control-label text-error">Название</form:label>
    <div class="controls">
      <form:input path="name" size="100%"/>
      <form:errors path="name" cssClass="text-error" element="span"/>
    </div>
  </div>
  <div class="control-group">
    <form:label path="startDate" cssClass="control-label" cssErrorClass="control-label text-error">Дата начала приема</form:label>
    <div class="controls">
      <form:input path="startDate"  size="100%"/>
      <form:errors path="startDate" cssClass="text-error" element="span"/>
    </div>
  </div>
  <div class="control-group">
    <form:label path="endDate" cssClass="control-label" cssErrorClass="control-label text-error">Дата окончания приема</form:label>
    <div class="controls">
      <form:input path="endDate" size="100%"/>
      <form:errors path="endDate" cssClass="text-error" element="span"/>
    </div>
  </div>
  <div class="control-group">
    <form:label path="availableTypes" cssClass="control-label" cssErrorClass="control-label text-error">Возможные типы обращений</form:label>
    <div class="controls">
      <form:checkboxes items="${applianceTypes}" itemLabel="name" itemValue="id" path="availableTypes" size="100%"/>
      <form:errors path="availableTypes" cssClass="text-error" element="span"/>
    </div>
  </div>
  <div class="control-group">
    <form:label path="active" cssClass="control-label" cssErrorClass="control-label text-error">Активный</form:label>
    <div class="controls">
      <form:checkbox path="active" size="100%" />
      <form:errors path="active" cssClass="text-error" element="span"/>
    </div>
  </div>

  <div class="control-group">
    <div class="controls">
      <a class="btn" href="<c:url value="/admin/StoredProperties.htm"/>">Назад</a>
      <button type="submit" class="btn btn-primary">Сохранить</button>
    </div>
  </div>
</form:form>

