<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="column-form faces list">
  <div class="hd">
    <h2>Список типов заявлений</h2>
  </div>
  <div class="bd">

    <table>
      <tr>
        <th>Название</th>
        <th>Полное название</th>
      </tr>

      <c:forEach items="${applianceTypes}" var="type">
        <tr>
          <td>
            <a href="<c:url value="/admin/ApplianceTypeEdit.htm"><c:param name="id" value="${type.id}"/></c:url> ">${type.name}</a>
          </td>
          <td>${type.description}</td>
        </tr>
      </c:forEach>
    </table>
    <p><a href="<c:url value="/admin/ApplianceTypeEdit.htm"/>">Создать новый тип</a></p>
  </div>
</div>