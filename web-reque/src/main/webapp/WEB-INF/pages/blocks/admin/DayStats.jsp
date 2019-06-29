<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<script>

  $( function(){
    var from =$("#testDate").datepicker();
    $("#testDate").datepicker("option", $.datepicker.regional[ "ru" ]);
  });
</script>


<div>
  <h2>Данные на день</h2>

  <form action="<c:url value="/admin/DayStats.htm"/>" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <div class="form-group row">
      <label class="col-sm-2 col-form-label" for="testDate">Дата</label>
      <div class="col-sm-5">
        <input type="text" id="testDate" name="testDate" value="<fmt:formatDate value="${testDate}" pattern="dd.MM.yyyy"/>"  class="form-control">
      </div>
    </div>
    <div class="form-group row">
      <div class="col-sm-5">
        <a class="btn btn-outline-success" href="<c:url value="/home.htm"/>">Назад</a>
        <button type="submit" class="btn btn-primary">Показать данные</button>
      </div>
    </div>
  </form>

  <div class="row">
    <div class="col-sm-5"><fmt:formatDate value="${testDate}" pattern="EEEE, d MMMM yyyy"/></div>
    <div class="col-sm-7">
      <fmt:formatDate value="${testDate}" pattern="dd.MM.yyyy" var="strTestDate"/>
      <a target="_blank" class="btn btn-outline-info" href="<c:url value="/admin/DayStats.htm"><c:param name="testDate" value="${strTestDate}"/><c:param name="print" value="1"/></c:url>">Печать списка</a>
      <a target="_blank" class="btn btn-outline-info" href="<c:url value="/admin/DayStats.htm"><c:param name="testDate" value="${strTestDate}"/><c:param name="ticket" value="1"/></c:url>">Печать талонов</a>
    </div>
  </div>
  <table class="table" >
    <tr>
      <c:forEach items="${types}" var="appType">
        <th>${appType.name}</th>
      </c:forEach>
      <th>Итого</th>
    </tr>
    <c:forEach items="${stats}" var="entry">
      <tr>
        <c:set var="dayStats" value="${entry.value}"/>
        <c:set var="total" value="0"/>
        <c:forEach items="${types}" var="appType">
          <td align="center">${dayStats[appType]}</td>
          <c:set var="total" value="${total + dayStats[appType]}"/>
        </c:forEach>
        <td align="center"><strong>${total}</strong></td>
      </tr>
    </c:forEach>

  </table>

  <table class="table">
    <tr>
      <th>#</th>
      <th>Время</th>
      <th>Имя</th>
      <th>Тип</th>
      <th>Онлайн номер</th>
    </tr>
    <c:forEach items="${appointments}" var="appt" varStatus="indx">
      <tr>
        <td>${indx.index+1}</td>
        <td><fmt:formatDate value="${appt.scheduledTime}" pattern="HH:mm"/></td>
        <td>
          <sec:authorize access="hasRole('ROLE_ADMIN')">
            <a href="<c:url value="/admin/EditAppointment.htm"><c:param name="id" value="${appt.id}"/></c:url>">${appt.user.lastName} ${appt.user.firstName}</a>
          </sec:authorize>
            <sec:authorize access="not hasRole('ROLE_ADMIN')">${appt.user.lastName} ${appt.user.firstName}</sec:authorize>
        </td>
        <td>${appt.type.name}</td>
        <td>${appt.onlineNumber}</td>
      </tr>
    </c:forEach>
  </table>
</div>