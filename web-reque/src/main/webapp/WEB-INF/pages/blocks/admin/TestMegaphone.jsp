<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<h2>Test SMS</h2>

<%--<c:if test="${not empty jsonResponse}"><p class="text-info">${jsonResponse}</p></c:if>--%>
<c:if test="${not empty resultMsgs}">
  <p class="text-info">
  <c:forEach items="${resultMsgs}" var="message">${message}<br/></c:forEach>
  </p>
</c:if>

<form action="<c:url value="/admin/SendSms.htm"/>" method="post" enctype="multipart/form-data">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

  <div class="form-group row">
    <label for="phoneNumber" class="col-sm-2 col-form-label">Телефонный номер</label>
    <div class="col-sm-5">
      <textarea id="phoneNumber" name="phoneNumber" class="form-control" rows="15"></textarea>
      <c:if test="${not empty phoneNumberMsg}"><span class="text-danger">${phoneNumberMsg}</span></c:if>
    </div>
  </div>
  <div class="form-group row">
    <label for="phonesFile" class="col-sm-2 col-form-label">Select a file:</label>
    <div class="col-sm-5">
        <input id="phonesFile" type="file" name="phonesListFile" class="form-control-file"/>
    </div>
  </div>
  <div class="form-group row">
    <label for="text" class="col-sm-2 col-form-label" >Текст SMS</label>
    <div class="col-sm-5">
      <textarea id="text" name="text" class="form-control" rows="15"></textarea>
    </div>
  </div>

  <div class="form-group row">
    <div class="col-sm-5">
      <button class="btn btn-primary" type="submit">Отправить</button>
    </div>
  </div>
</form>
