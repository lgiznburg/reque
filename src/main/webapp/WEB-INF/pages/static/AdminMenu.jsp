<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<ul class="nav nav-sidebar">
  <c:set var="uri" value="${pageContext.request.requestURI}"/>
  <li <c:if test="${fn:contains(uri, '/admin/StoredProperties.htm')}">class="active"</c:if> ><a href="<c:url value="/admin/StoredProperties.htm"/>">Настройки</a></li>
  <li <c:if test="${fn:contains(uri, '/admin/ApplianceTypes.htm')}">class="active"</c:if> ><a href="<c:url value="/admin/ApplianceTypes.htm"/> ">Типы заявлений</a></li>
  <li <c:if test="${fn:contains(uri, '/admin/Campaigns.htm')}">class="active"</c:if> ><a href="<c:url value="/admin/Campaigns.htm"/> ">Приемные кампании</a></li>
  <li <c:if test="${fn:contains(uri, '/admin/Statistics.htm')}">class="active"</c:if> ><a href="<c:url value="/admin/Statistics.htm"/> ">Статистика</a></li>
</ul>
