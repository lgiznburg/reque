<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<link rel="stylesheet" href="<c:url value="/resources/css/jquery.timepicker.css"/> ">

<script src="<c:url value="/resources/js/jquery.timepicker.min.js"/> "></script>

<script>

  $( function() {

    $("#scheduledTime").timepicker({
      minTime: <fmt:formatDate value="${startTime}" pattern="''H:mm''" />,
      maxTime: <fmt:formatDate value="${endTime}" pattern="''H:mm''" />,
      show2400: true,
      timeFormat: 'H:i',
      step: ${granularity},
      disableTextInput: true,
      orientation: 'bl'
    });

    showTime( '<fmt:formatDate value="${startDate}" pattern="dd.MM.yyyy"/>' );

  });

  function showTime( date ) {
    $("#timeSection").show();
    $.ajax( '<c:url value="/ajax/GetAppointmentTimes.htm"/>',
        {
          data : {
            date : date,
            dataType: "text"
          }
        }
    )
        .done( function setDisableTimes( data ) {
          $("#scheduledTime").timepicker( "option", "disableTimeRanges", data );
        } );
  }


</script>

<h2>Запись на сегодняшний день</h2>

<form:form commandName="appointmentToCreate" name="appointment" method="post" action="CreateTodayAppointment.htm">
  <form:hidden path="campaign"/>

  <div class="form-group row">
    <form:label path="userEmail" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">E-mail <sup>*</sup></form:label>
    <div class="col-sm-5">
      <form:input path="userEmail"  class="form-control"/>
      <form:errors path="userEmail" element="span" cssClass="text-danger" />
    </div>
  </div>
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
    <form:label path="onlineNumber" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Код онлайн регистрации <sup>*</sup></form:label>
    <div class="col-sm-5">
      <form:input path="onlineNumber"  cssClass="form-control"/>
      <form:errors path="onlineNumber" element="span" cssClass="text-danger" />
    </div>
  </div>
  <div class="form-group row">
    <form:label path="type" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Тип заявления <sup>*</sup></form:label>
    <div class="col-sm-5">
      <form:select path="type" items="${applianceTypes}" itemLabel="description" itemValue="id"   cssClass="form-control"/>
      <form:errors path="type" element="span" cssClass="text-danger"/>
    </div>
  </div>
  <%--<div class="form-group row">
    <form:label path="scheduledDate" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Дата <sup>*</sup></form:label>
    <div class="col-sm-5">
      <form:input path="scheduledDate"   cssClass="form-control"/>
      <form:errors path="scheduledDate" element="span" cssClass="text-danger"/>
    </div>
  </div>--%>
  <div class="form-group row">
    <form:label path="scheduledTime" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Время <sup>*</sup></form:label>
    <div class="col-sm-5">
      <form:input path="scheduledTime"  cssClass="form-control" />
      <form:errors path="scheduledTime" element="span" cssClass="text-danger"/>
    </div>
  </div>
  <div class="form-group row">
    <div class="col-sm-7">
      <a class="btn btn-outline-success" href="<c:url value="/home.htm"/>">Назад</a>
      <button type="submit" class="btn btn-primary">Сохранить</button>

    </div>
  </div>
</form:form>
