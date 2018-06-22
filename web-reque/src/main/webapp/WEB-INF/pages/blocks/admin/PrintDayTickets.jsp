<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
  <title>${title}</title>
  <style>
    td {border-top: solid black 1px;padding-bottom: 25px;padding-top: 25px;padding-left: 10px;}
  </style>
</head>
<body>
<p style="text-align: center; font-size: larger;"> <strong>Список на <fmt:formatDate value="${testDate}" pattern="dd MMMM yyyy"/></strong></p>
<table cellpadding="2" cellspacing="2" width="80%">
  <c:forEach items="${appointments}" var="appt" varStatus="indx">
    <tr>
      <td><strong>${appt.compoundId}</strong></td>
      <td><fmt:formatDate value="${appt.scheduledTime}" pattern="HH:mm"/></td>
      <td>${appt.user.lastName} ${appt.user.firstName}</td>
      <td>${appt.onlineNumber}</td>
    </tr>
  </c:forEach>

</table>

</body>
</html>