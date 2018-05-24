<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<nav class="nav flex-column">
  <c:set var="uri" value="${pageContext.request.requestURI}"/>
  <a class="nav-link <c:if test="${fn:contains(uri, '/admin/StoredProperties.htm')}">active</c:if>" href="<c:url value="/admin/StoredProperties.htm"/>">Настройки</a>
  <a class="nav-link <c:if test="${fn:contains(uri, '/admin/ApplianceTypes.htm')}">active</c:if>" href="<c:url value="/admin/ApplianceTypes.htm"/>">Типы заявлений</a>
  <a class="nav-link <c:if test="${fn:contains(uri, '/admin/Campaigns.htm')}">active</c:if>" href="<c:url value="/admin/Campaigns.htm"/>">Приемные кампании</a>
  <a class="nav-link <c:if test="${fn:contains(uri, '/admin/Statistics.htm')}">active</c:if>" href="<c:url value="/admin/Statistics.htm"/>">Статистика</a>
  <a class="nav-link <c:if test="${fn:contains(uri, '/admin/DayStats.htm')}">active</c:if>" href="<c:url value="/admin/DayStats.htm"/>">Данные на день</a>
</nav>
