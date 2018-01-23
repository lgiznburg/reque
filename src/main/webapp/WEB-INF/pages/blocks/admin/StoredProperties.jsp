<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="column-form faces list">
  <div class="hd">
    <h2>List of System Properties</h2>
  </div>
  <div class="bd">

    <table>
      <tr class="region-name">
        <th>Property Group</th>
        <th>Property Name</th>
        <th>Property Value</th>
      </tr>

      <c:set value="" var="group"/>
      <c:forEach items="${storedProperties}" var="storedProperty">
        <tr class="region-name" <c:if test="${group ne storedProperty.propertyName.groupName}">style="border-top: solid black 1px"</c:if> >
          <td>
            <c:if test="${group ne storedProperty.propertyName.groupName}">
              ${storedProperty.propertyName.groupName}
              <c:set var="group" value="${storedProperty.propertyName.groupName}"/>
            </c:if>&nbsp;
          </td>
          <td>
            <c:url value="/admin/StoredPropertyEdit.htm" var="editUrl"><c:param name="propertyName" value="${storedProperty.propertyName}"/></c:url>
            <a href="${editUrl}">${storedProperty.propertyName.name}</a>
          </td>
          <td><a href="${editUrl}">${storedProperty.value}</a></td>
        </tr>
      </c:forEach>
    </table>
  </div>
</div>