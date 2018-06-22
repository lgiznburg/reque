<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<h2>Запись на сегодняшний день завершена.</h2>

<p>Назначено время - сегодня в <strong><fmt:formatDate value="${createdAppointment.scheduledTime}" pattern="HH:mm"/></strong>. </p>

<div class="row">
  <div class="col-sm-10">
    <fmt:formatDate value="${createdAppointment.scheduledDate}" pattern="dd.MM.yyyy" var="strTestDate"/>
    <a target="_blank" class="btn btn-outline-info" href="<c:url value="/admin/DayStats.htm">
    <c:param name="testDate" value="${strTestDate}"/><c:param name="ticket" value="1"/>
    <c:param name="appointment" value="${createdAppointment.id}"/>
    </c:url>">Печать талона</a>
  </div>
</div>
