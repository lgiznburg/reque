<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

  <h2>Выберите цель обращения</h2>
  <p>
    <c:forEach var="campaign" items="${campaigns}">
        <a class="btn btn-block" href="<c:url value="/CreateAppointment.htm"><c:param name="campaign" value="${campaign.id}"/></c:url>">${campaign.name}
        <fmt:formatDate value="${campaign.startDate}" dateStyle="medium"/> - <fmt:formatDate value="${campaign.endDate}" dateStyle="medium"/></a>
    </c:forEach>
  </p>
