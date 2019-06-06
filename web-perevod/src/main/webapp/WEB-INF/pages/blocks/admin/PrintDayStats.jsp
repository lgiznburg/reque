<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
  <title>${title}</title>
</head>
<body>
<p style="text-align: center; font-size: larger;"> <strong>Список на <fmt:formatDate value="${testDate}" pattern="dd MMMM yyyy"/></strong></p>
<table cellpadding="2" cellspacing="2" width="80%">
  <tr>
    <th>#</th>
    <th>Время</th>
    <th>ФИО</th>
    <th>Тип</th>
    <th>Окончание сессии</th>
    <th>Документ</th>
    <th>Телефон</th>
  </tr>
  <c:forEach items="${appointments}" var="appt" varStatus="indx">
    <tr>
      <td>${indx.index+1}</td>
      <td><fmt:formatDate value="${appt.scheduledTime}" pattern="HH:mm"/></td>
      <td>${appt.user.lastName} ${appt.user.firstName} ${appt.user.additionalUserInfo.middleName}
        <c:if test="${not empty appt.user.additionalUserInfo.representativeFullName}">(представитель: ${appt.user.additionalUserInfo.representativeFullName} )</c:if>
      </td>
      <td>${appt.type.name}</td>
      <td><fmt:formatDate value="${appt.user.additionalUserInfo.sessionEndDate}" pattern="dd.MM.yyyy"/></td>
      <td>${appt.onlineNumber}</td>
      <td>${appt.user.phoneNumber}</td>
    </tr>
  </c:forEach>

</table>

</body>
</html>