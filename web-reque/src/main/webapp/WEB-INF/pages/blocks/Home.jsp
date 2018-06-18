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

<h2>Электронная запись на прием документов.</h2>
<p class="text-info"><strong>Уважаемые абитуриенты!</strong></p>
<p>Приемная комиссия стремится сделать подачу заявления на поступление в наш университет как можно белее простой и удобной.
  Мы хотим, чтобы Вы тратили как можно меньше времени на ожидание, поэтому мы принимаем заявления только по предварительной
записи.</p>

<c:if test="${empty appointment}">
  <p>Для ускорения подачи Вашего заявления рекомендуем предварительно заполнить <a class="btn btn-outline-success" href="https://reg1.rsmu.ru" target="_blank">электронную форму</a> </p>
  <p>Обратите внимание, эта форма требует отдельной регистрации. После заполнения формы электронного заявления нужно
    запомнить номер заявления и использовать этот номер при назначении даты и времени посещения приемной комиссии.</p>
</c:if>

  <sec:authorize access="isAnonymous()">
   <p>Для записи в очередь для подачи заявления необходимо сначала <a class="btn btn-primary" href="<c:url value="Registration.htm"/> ">зарегистрироваться</a>
      После этого Вы сможете выбрать день и время посещения из доступных на текущий момент. Позже Вы сможете перенести Вашу записть на другое время. </p>
  </sec:authorize>
  <sec:authorize access="hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')">
    <c:choose>
      <c:when test="${empty appointment}">
        <p><a class="btn btn-primary"  href="<c:url value="SelectCampaign.htm"/> ">Назначить дату и время</a> для подачи заявления. </p>
      </c:when>
      <c:otherwise>
        <p>Мы Вас ожидаем в<c:if test="${fn:contains(fullDate, 'вторник')}">о</c:if>  <strong>${fullDate}</strong> <%-- <fmt:formatDate value="${appointment.scheduledDate}" pattern="EEEE, dd MMMM"/>--%>
          в <strong><fmt:formatDate value="${appointment.scheduledTime}" pattern="HH:mm"/></strong>. </p>
        <p>Вам необходимо предоставить следующие документы:
        <ul>
          <c:forEach items="${appointment.type.documents}" var="document"><li>${document.name}</li></c:forEach>
        </ul>
        </p>
        <p>Вы можете <a class="btn btn-primary" href="<c:url value="/CreateAppointment.htm"><c:param name="id" value="${appointment.id}"/></c:url>">изменить</a> Вашу запись. </p>
      </c:otherwise>
    </c:choose>
  </sec:authorize>

