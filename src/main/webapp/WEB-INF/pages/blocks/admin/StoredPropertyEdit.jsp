<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


    <h2></h2>

<form:form commandName="storedProperty" action="StoredPropertyEdit.htm" method="post" name="storedProperty" cssClass="form-horizontal">
      <form:hidden path="propertyName"/>
      <table>
        <tr class="region-name">
          <th>
              ${storedProperty.propertyName.groupName}
          </th>
          <th>
              ${storedProperty.propertyName.name}
          </th>
        </tr>
        <tr>
          <td colspan="2">
            <form:input path="value" size="100%"/>
            <form:errors path="value" cssStyle="color:red;"/>
          </td>
        </tr>
      </table>

  <div class="control-group">
    <div class="controls">
      <a class="btn" href="<c:url value="/admin/StoredProperties.htm"/>">Назад</a>
      <button type="submit" class="btn btn-primary">Сохранить</button>
    </div>
  </div>
    </form:form>

