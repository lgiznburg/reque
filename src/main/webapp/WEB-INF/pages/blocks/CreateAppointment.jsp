<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<link rel="stylesheet" href="<c:url value="/resources/css/jquery.timepicker.css"/> ">

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

    <c:if test="${appointment.id ne 0}">
    showTime( $("#scheduledTime").datepicker( "getDate" ) );
    </c:if>

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

  function onShowDate( date ) {
    if ( typeof dateMap.get( date.toDateString() ) != "undefined" ) {
      return [ false, "", dateMap.get(date.toDateString()) ];
    }
    return [true, "", ""];
  }


</script>

<h2>Create Appointment</h2>

<form:form commandName="appointmentToCreate" name="appointment" method="post" action="CreateAppointment.htm" cssClass="form-horizontal">
  <form:hidden path="id"/>
  <form:hidden path="campaign"/>
  <div class="control-group">
    <form:label path="onlineNumber" cssClass="control-label" cssErrorClass="control-label text-error">Код онлайн регистрации <sup>*</sup></form:label>
    <div class="controls">
      <form:input path="onlineNumber" />
      <form:errors path="onlineNumber" element="span" cssClass="text-error" />
    </div>
  </div>
  <div class="control-group">
    <form:label path="type" cssClass="control-label" cssErrorClass="control-label text-error">Тип заявления <sup>*</sup></form:label>
    <div class="controls">
      <form:select path="type" items="${applianceTypes}" itemLabel="description" itemValue="id" />
      <form:errors path="type" element="span" cssClass="text-error"/>
    </div>
  </div>
  <div class="control-group">
    <form:label path="scheduledDate" cssClass="control-label" cssErrorClass="control-label text-error">Дата <sup>*</sup></form:label>
    <div class="controls">
      <form:input path="scheduledDate" />
      <form:errors path="scheduledDate" element="span" cssClass="text-error"/>
    </div>
  </div>
  <div class="control-group">
    <form:label path="scheduledTime" cssClass="control-label" cssErrorClass="control-label text-error">Время <sup>*</sup></form:label>
    <div class="controls">
      <form:input path="scheduledTime" />
      <form:errors path="scheduledTime" element="span" cssClass="text-error"/>
    </div>
  </div>
  <div class="control-group">
    <div class="controls">
      <a class="btn" href="<c:url value="/home.htm"/>">Назад</a>
      <button type="submit" class="btn btn-primary">Сохранить</button>
      <c:if test="${appointmentToCreate.id > 0}">
        <button type="submit" name="delete" class="btn">Удалить</button>
      </c:if>
    </div>
  </div>
</form:form>
