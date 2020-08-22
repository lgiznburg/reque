<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

  <h2><spring:message code="select_campaign.goal"/></h2>
  <ul>
    <c:forEach var="campaign" items="${campaigns}">
        <li><a class="btn" href="<c:url value="/CreateAppointment.htm"><c:param name="campaign" value="${campaign.id}"/></c:url>">${campaign.name}
        &nbsp;&nbsp;&nbsp;<fmt:formatDate value="${campaign.startDate}" dateStyle="medium"/> - <fmt:formatDate value="${campaign.endDate}" dateStyle="medium"/></a></li>
    </c:forEach>
  </ul>
<c:if test="${empty campaigns}">
  <p><spring:message code="select_campaign.no_campaign"/> <a href="http://rsmu.ru/abitur1.html"><spring:message code="basic.our_site"/></a>.</p>
</c:if>
