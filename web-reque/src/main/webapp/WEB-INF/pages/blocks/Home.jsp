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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h2><spring:message code="home.title"/></h2>
<p class="text-info"><strong><spring:message code="home.greeting"/> </strong></p>
<p><spring:message code="home.goal_text"/></p>

<c:if test="${empty appointment}">
<%--
  <div class="row">
    <div class="col p-2">
      <span class="text-info">Рекомендация:</span> для ускорения подачи Вашего заявления предварительно заполните электронную форму
    </div>
  </div>
  <div class="row justify-content-center">
    <div class="col-auto justify-content-center p-2">
      <a class="btn btn-outline-success" href="https://reg1.rsmu.ru" target="_blank">для поступающих на 1-й курс</a><br/>
    </div>
    <div class="col-auto justify-content-center p-2">
      <a class="btn btn-outline-success" href="https://regmst.rsmu.ru" target="_blank">для поступающих в магистратуру</a><br/>
    </div>
    <div class="col-auto justify-content-center p-2">
      <a class="btn btn-outline-success" href="https://regkvk.rsmu.ru" target="_blank">для поступающих в ординатуру</a>
    </div>
  </div>
  <div class="row">
    <div class="col p-2">
      <span class="text-info">Обратите внимание</span>, эта форма требует отдельной регистрации. После заполнения формы электронного заявления
      запомните <em>номер заявления</em> и используйте этот номер при назначении даты и времени посещения приемной комиссии.
    </div>
  </div>
--%>
  ${announce}
</c:if>

  <sec:authorize access="isAnonymous()">
    <div class="row">
      <div class="col p-2">
        <spring:message code="home.appointment_requirement"/>
      </div>
    </div>
    <div class="row justify-content-center">
      <div class="col-auto p-2">
        <a class="btn btn-primary" href="<c:url value="Registration.htm"/> "><spring:message code="basic.register"/> </a>
      </div>
    </div>
    <div class="row">
      <div class="col p-2">
        <spring:message code="home.after_signup"/>
      </div>
    </div>
  </sec:authorize>
  <sec:authorize access="hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')">
    <c:choose>
      <c:when test="${empty appointment}">
        <p><span  class="text-info"><strong><spring:message code="home.attention"/> </strong></span></p>

        <div class="row justify-content-center">
          <div class="col-auto p-2">
            <a class="btn btn-primary"  href="<c:url value="SelectCampaign.htm"/> "><spring:message code="basic.set_appointment"/> </a>
          </div>
        </div>
      </c:when>
      <c:otherwise>
        <div class="row">
          <div class="col p-2">
            <p>${awaiting_msg}</p>
            <p><spring:message code="home.provide_docs"/>
            <ul>
              <c:forEach items="${appointment.type.documents}" var="document"><li>${document.name}</li></c:forEach>
            </ul>
            </p>
          </div>
        </div>
        <div class="row justify-content-center">
          <div class="col-auto p-2">
            <a class="btn btn-primary" href="<c:url value="/CreateAppointment.htm"><c:param name="id" value="${appointment.id}"/></c:url>"><spring:message code="basic.change_appointment"/> </a>
          </div>
        </div>
      </c:otherwise>
    </c:choose>
  </sec:authorize>

