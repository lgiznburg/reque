<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<div class="column-form">
  <div class="hd">
    <h2>Edit Field Type</h2>
  </div>
  <div class="bd">
    <form:form commandName="applianceType" action="ApplianceTypeEdit.htm" method="post" name="applianceType">
      <form:hidden path="id"/>
      <div>
        <form:label path="name" for="name">Название</form:label>
        <form:input path="name" size="100%"/>
        <form:errors path="name" cssStyle="color:red;"/>
      </div>
      <div>
        <form:label path="description" for="name">Полное название</form:label>
        <form:input path="description" size="100%"/>
        <form:errors path="description" cssStyle="color:red;"/>
      </div>

      <div>
        <input type="submit" value="save"/>
        &nbsp;
        <a href="<c:url value="/admin/ApplianceTypes.htm"/>"><button  value="cancel">cancel</button></a>
      </div>
    </form:form>
  </div>
  <div class="ft"></div>
</div>

