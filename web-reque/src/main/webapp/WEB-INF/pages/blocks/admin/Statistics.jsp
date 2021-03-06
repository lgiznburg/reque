<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div>
  <table class="table" >
    <tr>
      <th>Date</th>
      <th>Данные</th>
      <th>Итого</th>
      <%--<c:forEach items="${types}" var="appType">
        <th>${appType.name}</th>
      </c:forEach>--%>
    </tr>
    <c:forEach items="${stats}" var="entry">
      <tr>
        <td align="center"><fmt:formatDate value="${entry.key}" pattern="dd.MM.yyyy" var="testDate"/>
          <a href="<c:url value="/admin/DayStats.htm"><c:param name="testDate" value="${testDate}"/></c:url>">
            <fmt:formatDate value="${entry.key}" pattern="EEE, d MMM yyyy"/>
          </a>
        </td>
        <c:set var="total" value="0"/>
        <c:set var="dayStats" value="${entry.value}"/>
        <td align="center">
          <c:forEach items="${types}" var="appType">
            <c:if test="${not empty dayStats[appType]}">
              ${appType.name} - <strong>${dayStats[appType]}</strong>,
              <c:set var="total" value="${total + dayStats[appType]}"/>
            </c:if>
          </c:forEach>
        </td>
        <td align="center"><strong>${total}</strong></td>
      </tr>
    </c:forEach>

  </table>
</div>