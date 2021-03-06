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


  function onShowDate( date ) {
    if ( typeof dateMap.get( date.toDateString() ) != "undefined" ) {
      return [ false, "", dateMap.get(date.toDateString()) ];
    }
    return [true, "", ""];
  }


</script>

<h2>Изменить предварительную запись</h2>

<form:form commandName="appointmentToCreate" name="appointment" method="post" action="EditAppointment.htm">
  <form:hidden path="id"/>

  <div class="row">
    <div class="col-sm-2">ФИО</div>
    <div class="col-sm-5">
      <p>${appointmentToCreate.user.lastName} ${appointmentToCreate.user.firstName} ${appointmentToCreate.user.additionalUserInfo.middleName}</p>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-2">Гражданство</div>
    <div class="col-sm-5">
      <p>${appointmentToCreate.user.additionalUserInfo.citizenship}</p>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-2">Тип заявления</div>
    <div class="col-sm-5">
      <p>${appointmentToCreate.type.description}</p>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-2">Окончание сессии</div>
    <div class="col-sm-5">
      <p><fmt:formatDate value="${appointmentToCreate.user.additionalUserInfo.sessionEndDate}" pattern="dd MMM yyyy"/></p>
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
      <a class="btn btn-outline-success" href="<c:url value="/admin/DayStats.htm"><c:param name="testDate" value="${appointmentToCreate.scheduledDate}"/></c:url>">Назад</a>
      <button type="submit" class="btn btn-primary">Сохранить</button>
      <c:if test="${appointmentToCreate.id > 0}">
        <button type="button" id="deleteButton" class="btn btn-outline-warning">Удалить</button>
        <div id="sureDialog">Запись будет удалена. Вы уверены?</div>
      </c:if>
    </div>
  </div>
</form:form>
