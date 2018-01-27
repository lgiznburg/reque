<%--
  Created by IntelliJ IDEA.
  User: leonid
  Date: 17.12.17
  Time: 21:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
  <title>${title}</title>

  <link rel="stylesheet" href="<c:url value="/resources/css/jquery-ui.min.css"/>"/>

  <script src="<c:url value="/resources/js/jquery.js"/>"></script>
  <script src="<c:url value="/resources/js/jquery-ui.min.js"/> "></script>
  <script src="<c:url value="/resources/js/datepicker-ru.js"/> "></script>
</head>
<body>
<div id="header">
  <c:import url="/WEB-INF/pages/static/Header.jsp"/>
  <div id="menu">
    <sec:authorize access="hasRole('ROLE_ADMIN')">
      <table>
        <tr>
          <td><a href="<c:url value="/admin/StoredProperties.htm"/>">Настройки</a></td>
          <td><a href="<c:url value="/admin/ApplianceTypes.htm"/> ">Типы заявлений</a> </td>
          <td><a href="<c:url value="/admin/Campaigns.htm"/> ">Приемные кампании</a> </td>
          <td><a href="<c:url value="/admin/Statistics.htm"/> ">Статистика</a> </td>
        </tr>
      </table>
    </sec:authorize>
  </div>
</div>
<div id="content">
  <c:import url="${content}"/>
</div>

</body>
</html>
