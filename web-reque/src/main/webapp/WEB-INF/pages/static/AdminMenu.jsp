<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<nav class="nav flex-column">
  <c:set var="uri" value="${pageContext.request.requestURI}"/>
  <sec:authorize access="hasRole('ROLE_ADMIN')">

    <a class="nav-link <c:if test="${fn:contains(uri, '/admin/StoredProperties.htm')}">active</c:if>" href="<c:url value="/admin/StoredProperties.htm"/>">Настройки</a>
    <a class="nav-link <c:if test="${fn:contains(uri, '/admin/Documents.htm')}">active</c:if>" href="<c:url value="/admin/Documents.htm"/>">Документы</a>
    <a class="nav-link <c:if test="${fn:contains(uri, '/admin/ApplianceTypes.htm')}">active</c:if>" href="<c:url value="/admin/ApplianceTypes.htm"/>">Типы заявлений</a>
    <a class="nav-link <c:if test="${fn:contains(uri, '/admin/Campaigns.htm')}">active</c:if>" href="<c:url value="/admin/Campaigns.htm"/>">Приемные кампании</a>
    <a class="nav-link <c:if test="${fn:contains(uri, '/admin/EditAnnounce.htm')}">active</c:if>" href="<c:url value="/admin/EditAnnounce.htm"/>">Текст на первой странице</a>
  </sec:authorize>
  <sec:authorize access="hasAnyRole('ROLE_SERVICEMAN','ROLE_ADMIN')">
    <a class="nav-link <c:if test="${fn:contains(uri, '/admin/Statistics.htm')}">active</c:if>" href="<c:url value="/admin/Statistics.htm"/>">Статистика</a>
    <a class="nav-link <c:if test="${fn:contains(uri, '/admin/DayStats.htm')}">active</c:if>" href="<c:url value="/admin/DayStats.htm"/>">Данные на день</a>
    <a class="nav-link <c:if test="${fn:contains(uri, '/admin/CreateTodayAppointment.htm') or fn:contains(uri, '/admin/SelectCampaign.htm')}">active</c:if>" href="<c:url value="/admin/SelectCampaign.htm"/>">Записать на сегодня</a>
  </sec:authorize>
  <sec:authorize access="hasAnyRole('ROLE_MANAGER','ROLE_ADMIN')">
    <a class="nav-link <c:if test="${fn:contains(uri, '/admin/SendSms.htm')}">active</c:if>" href="<c:url value="/admin/SendSms.htm"/>">Рассылка СМС</a>
  </sec:authorize>
</nav>
