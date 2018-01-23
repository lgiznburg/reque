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
      onSelect: showTime
    } );
    $("#scheduledDate").datepicker( "option", $.datepicker.regional[ "ru" ] );

    $("#scheduledTime").timepicker({
      minTime: <fmt:formatDate value="${startTime}" pattern="''H:mm''" />,
      maxTime: <fmt:formatDate value="${endTime}" pattern="''H:mm''" />,
      show2400: true,
      timeFormat: 'H:i',
      step: ${granularity},
      disableTextInput: true,
      orientation: 'bl'
    })
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

<div class="column-form">
  <div class="hd">
    <h2>Create Appointment</h2>
  </div>
  <div class="bd">
    <form:form commandName="appointment" name="appointment" method="post" action="CreateAppointment.htm">
      <form:hidden path="id"/>
      <table>
        <tr>
          <th>Online Code:</th>
          <td>
            <form:input path="onlineNumber"/> <br/><form:errors path="onlineNumber"/>
          </td>
        </tr>
        <tr>
          <th>Type of Appliance:</th>
          <td>
            <form:select path="type" items="${applianceTypes}" itemLabel="description" itemValue="id"/>
            <br/><form:errors path="type"/>
          </td>
        </tr>
        <tr>
          <th>Date:</th>
          <td>
            <form:input path="scheduledDate" /> <br/><form:errors path="scheduledDate"/>
          </td>
        </tr>
        <tr id="timeSection" style="display: none;">
          <th>Time:</th>
          <td>
            <form:input path="scheduledTime" /> <br/><form:errors path="scheduledTime"/>
          </td>
        </tr>
        <tr>
          <td colspan="2">
            <input type="submit" name="save" value="Save">
          </td>
        </tr>
      </table>
    </form:form>
  </div>
</div>
