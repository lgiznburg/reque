<%--
  Created by IntelliJ IDEA.
  User: leonid
  Date: 24.12.17
  Time: 2:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="content" style="align-content: center; clear: both;">
  <div><h2>Предварительная запись на подачу заявления.</h2></div>

  <sec:authorize access="isAnonymous()">
    <p>Для подачи заявления необходимо <a href="<c:url value="Registration.htm"/> ">зарегистрироваться</a> </p>
  </sec:authorize>
  <sec:authorize access="hasAnyRole('ROLE_CLIENT','ROLE_ADMIN')">
    <p><a href="<c:url value="CreateAppointment.htm"/> ">Назначить дату и время</a> для подачи заявления. </p>
  </sec:authorize>

</div>