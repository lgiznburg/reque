<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h2>Список Названий документов</h2>

<table class="table">
  <tr>
    <th>Порядковый номер</th>
    <th>Название</th>
  </tr>

  <c:forEach items="${documentNames}" var="document">
    <tr>
      <td>${document.order}</td>
      <td>
        <a href="<c:url value="/admin/DocumentNameEdit.htm"><c:param name="id" value="${document.id}"/></c:url> ">${document.name}</a>
      </td>
    </tr>
  </c:forEach>
</table>
<p>
  <a class="btn btn-primary" href="<c:url value="/admin/DocumentNameEdit.htm"/>">Создать новый документ</a>
</p>
