<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h2>Список приемных кампаний</h2>
<table class="table">
  <tr>
    <th>Название кампании</th>
    <th>Сроки поведения</th>
  </tr>
  <c:forEach var="campaign" items="${campaigns}">
    <tr>
      <td>
        <a href="<c:url value="/admin/CampaignEdit.htm"><c:param name="id" value="${campaign.id}"/></c:url>">${campaign.name}</a>
      </td>
      <td>
        <fmt:formatDate value="${campaign.startDate}" dateStyle="medium"/> - <fmt:formatDate value="${campaign.endDate}" dateStyle="medium"/>
      </td>
    </tr>
  </c:forEach>
</table>
<p>
  <a class="btn btn-primary" href="<c:url value="/admin/CampaignEdit.htm"/>">Создать новую Приемную кампанию</a>
</p>
