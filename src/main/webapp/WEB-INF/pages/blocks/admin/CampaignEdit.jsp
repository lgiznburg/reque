<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script>

  $( function(){
    var from =$("#startDate").datepicker()
        .on("change", function() {
              to.datepicker( "option", "minDate", getDate( this ) );
            }),
    to = $("#endDate").datepicker()
        .on("change", function() {
          from.datepicker( "option", "maxDate", getDate( this ) );
        });
    $("#startDate").datepicker("option", $.datepicker.regional[ "ru" ]);
    $("#endDate").datepicker("option", $.datepicker.regional[ "ru" ]);

    var dateFormat = "dd.mm.yy";
    function getDate( element ) {
      var date;
      try {
        date = $.datepicker.parseDate( dateFormat, element.value );
      } catch( error ) {
        date = null;
      }

      return date;
    }

  });
</script>

<div class="column-form">
  <div class="hd">
    <h2>Редактирование кампании</h2>
  </div>
  <div class="bd">
    <form:form commandName="campaign" action="CampaignEdit.htm" method="post" name="campaign">
      <form:hidden path="id"/>
      <table>
        <tr>
          <th><form:label path="name">Название</form:label></th>
          <td>
            <form:input path="name" size="100%"/>
            <form:errors path="name" cssStyle="color:red;"/>
          </td>
        </tr>
        <tr>
          <th><form:label path="startDate">Дата начала приема</form:label></th>
          <td>
            <form:input path="startDate"  size="100%"/>
            <form:errors path="startDate" cssStyle="color:red;"/>
          </td>
        </tr>
        <tr>
          <th><form:label path="endDate">Дата окончания приема</form:label></th>
          <td>
            <form:input path="endDate" size="100%"/>
            <form:errors path="endDate" cssStyle="color:red;"/>
          </td>
        </tr>
        <tr>
          <th><form:label path="availableTypes">Возможные типы обращений</form:label></th>
          <td>
            <form:checkboxes items="${applianceTypes}" itemLabel="name" itemValue="id" path="availableTypes" size="100%"/>
            <form:errors path="availableTypes" cssStyle="color:red;"/>
          </td>
        </tr>
        <tr>
          <th><form:label path="active">Активный</form:label></th>
          <td>
            <form:checkbox path="active" size="100%" />
            <form:errors path="active" cssStyle="color:red;"/>
          </td>
        </tr>

        <tr>
          <td colspan="2">
            <input type="submit" value="save"/>
            &nbsp;
            <a href="<c:url value="/admin/StoredProperties.htm"/>">cancel</a>
          </td>
        </tr>
      </table>
    </form:form>
  </div>
  <div class="ft"></div>
</div>

