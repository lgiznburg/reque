<%--
  Created by IntelliJ IDEA.
  User: leonid
  Date: 23.12.17
  Time: 22:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form commandName="queueStatus" method="post">
  <form:hidden path="id"/>
  <table>
    <tr>
      <th><form:label path="name">Название</form:label></th>
      <td><form:input path="name"/></td>
    </tr>
    <tr>
      <th><form:label path="description">Описание</form:label></th>
      <td><form:textarea path="description"/></td>
    </tr>
    <tr>
      <th><form:label path="type">Тип</form:label></th>
      <td><form:select path="type" items="${queueStatusTypes}"/> </td>
    </tr>
    <tr>
      <th></th>
      <td><input type="submit" value="Save"></td>
    </tr>
  </table>
</form:form>