<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<link rel="stylesheet" href="<c:url value="/resources/css/jquery.timepicker.css"/> ">

<style>
  .no-close .ui-dialog-titlebar-close {
    display: none;
  }
</style>

<script src="<c:url value="/resources/js/jquery.timepicker.min.js"/> "></script>

<script>
  var dateMap = new Map();
  <c:forEach items="${availableDates}" var="dateMap">
    dateMap.set( new Date( <fmt:formatDate value="${dateMap['date']}" pattern="yyyy, M-1, d"/>).toDateString(), '${dateMap['message']}' );
  </c:forEach>

  $( function() {
    $("#scheduledDate").datepicker( {
      minDate: new Date( <fmt:formatDate value="${startDate}" pattern="yyyy, M-1, d"/> ),
      maxDate: new Date( <fmt:formatDate value="${endDate}" pattern="yyyy, M-1, d"/> ),
      beforeShowDay: onShowDate,
      onSelect: showTime,
      showOptions: { direction: "down" },
      showAnim: "slideDown"
    } );
    //$("#scheduledDate").datepicker( "option", $.datepicker.regional[ "ru" ] );

    $("#scheduledTime").timepicker({
      minTime: <fmt:formatDate value="${startTime}" pattern="''H:mm''" />,
      maxTime: <fmt:formatDate value="${endTime}" pattern="''H:mm''" />,
      show2400: true,
      timeFormat: 'H:i',
      step: ${granularity},
      disableTextInput: true,
      orientation: 'bl'
    });

    <c:if test="${appointmentToCreate.id ne 0 or not empty appointmentToCreate.scheduledDate}">
    showTime( '<fmt:formatDate value="${appointmentToCreate.scheduledDate}" pattern="dd.MM.yyyy"/>' );
    </c:if>

    <c:if test="${appointmentToCreate.id ne 0}">
    $("#sureDialog").dialog({
      autoOpen : false,
      modal: true,
      dialogClass: "no-close border border-warning",
      buttons : [
        {
          text: "Да",
          click: function() {
            $('<input>').attr({
              type: 'hidden',
              id: 'delete',
              name: 'delete'
            }).appendTo('#appointmentToCreate');
            $("#appointmentToCreate").submit();
            $( this ).dialog( "close" );
          }
        },
        {
          text: "Нет",
          click: function() {
            $( this ).dialog( "close" );
          }
        }
      ]

    });
    $("#deleteButton").click( function() { $("#sureDialog").dialog("open"); return false; } );
    $("div.ui-dialog-buttonset button").addClass("btn btn-outline-warning");
    $("div.ui-dialog-titlebar").addClass("bg-white");
    </c:if>

  });

  function showTime( date ) {
    $("#timeSection").show();
    $.ajax( '<c:url value="/ajax/${appointmentToCreate.campaign.id}/GetAppointmentTimes.htm"/>',
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

  function onShowDate( date ) {
    if ( typeof dateMap.get( date.toDateString() ) != "undefined" ) {
      return [ false, "", dateMap.get(date.toDateString()) ];
    }
    return [true, "", ""];
  }


</script>

<h2>Предварительная запись</h2>

<form:form commandName="appointmentToCreate" name="appointment" method="post" action="CreateAppointment.htm">
  <form:hidden path="id"/>
  <form:hidden path="campaign"/>
  <div class="form-group row">
    <form:label path="onlineNumber" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Номер онлайн-заявления или Последние 4 цифры номера паспорта<sup>*</sup></form:label>
    <div class="col-sm-5">
      <form:input path="onlineNumber"  cssClass="form-control"/>
      <form:errors path="onlineNumber" element="span" cssClass="text-danger" />
    </div>
  </div>
  <div class="row">
    <div class="col-sm-5 offset-2 small text-info">
      <p>Для ускорения подачи Вашего заявления рекомендуем заполнить электронную форму <a href="https://reg1.rsmu.ru" target="_blank">для поступающих на 1-ый курс</a>,
        <a href="https://regmst.rsmu.ru" target="_blank">для поступающих в магистратуру</a>
        или <a href="https://regkvk.rsmu.ru" target="_blank">для поступающих в ординатуру</a>.</p>
      <p>Номер этого онлайн-заявления необходимо указать в предыдущем поле.</p>
      <p>Если Вы по каким-то причинам не желаете заполнять онлайн форму, введите последние 4 цифры номера Вашего паспорта.</p>
    </div>
  </div>
  <div class="form-group row">
    <form:label path="type" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Тип заявления <sup>*</sup></form:label>
    <div class="col-sm-5">
      <form:select path="type" items="${applianceTypes}" itemLabel="description" itemValue="id"   cssClass="form-control"/>
      <form:errors path="type" element="span" cssClass="text-danger"/>
    </div>
  </div>
  <div class="form-group row">
    <form:label path="scheduledDate" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Дата <sup>*</sup></form:label>
    <div class="col-sm-5">
      <form:input path="scheduledDate"   cssClass="form-control"/>
      <form:errors path="scheduledDate" element="span" cssClass="text-danger"/>
    </div>
  </div>
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
      <c:if test="${appointmentToCreate.id > 0}">
        <button type="button" id="deleteButton" class="btn btn-outline-warning">Удалить</button>
        <div id="sureDialog">Ваша запись будет удалена. Вы уверены?</div>
      </c:if>
    </div>
  </div>
</form:form>
