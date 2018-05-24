<%--
  Created by IntelliJ IDEA.
  User: leonid
  Date: 24.12.17
  Time: 2:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h2>Предварительная запись на подачу заявления на перевод.</h2>
<p class="text-info"><strong>Уважаемые студенты!</strong></p>
<p>Атестационная комиссия стремится сделать подачу заявления на поступление в наш университет как можно белее простой и удобной.
  Мы хотим, чтобы Вы тратили как можно меньше времени на ожидание, поэтому мы принимаем заявления только по предварительной
записи.</p>

  <sec:authorize access="isAnonymous()">
    <p>Для подачи заявления необходимо <a class="btn btn-primary" href="<c:url value="Registration.htm"/> ">зарегистрироваться</a> </p>
  </sec:authorize>
  <sec:authorize access="hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')">
    <c:choose>
      <c:when test="${empty appointment}">
        <p><a class="btn btn-primary"  href="<c:url value="SelectCampaign.htm"/> ">Назначить дату и время</a> для подачи заявления. </p>
      </c:when>
      <c:otherwise>
        <p>День, когда мы Вас ожидаем, - <fmt:formatDate value="${appointment.scheduledDate}" pattern="EEEE, dd MMMM"/>
          в <fmt:formatDate value="${appointment.scheduledTime}" pattern="HH:mm"/>. </p>
        <p>Вы можете <a class="btn btn-primary" href="<c:url value="/CreateAppointment.htm"><c:param name="id" value="${appointment.id}"/></c:url>">изменить</a> Вашу запись. </p>
      </c:otherwise>
    </c:choose>
  </sec:authorize>

