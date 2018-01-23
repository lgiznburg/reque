<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div>
  <table width="100%" border="1" cellpadding="5" >
    <tr>
      <th>Date</th>
      <c:forEach items="${types}" var="appType">
        <th>${appType.name}</th>
      </c:forEach>
    </tr>
    <c:forEach items="${stats}" var="entry">
      <tr>
        <td align="center"><fmt:formatDate value="${entry.key}" pattern="EEE, d MMM yyyy"/></td>
        <c:set var="dayStats" value="${entry.value}"/>
        <c:forEach items="${types}" var="appType">
          <td align="center">${dayStats[appType]}</td>
        </c:forEach>
      </tr>
    </c:forEach>

  </table>
</div>