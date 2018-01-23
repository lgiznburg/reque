<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<div class="column-form">
  <div class="hd">
    <h2>User Registration</h2>
  </div>
  <div class="bd">
    <form:form commandName="userToReg" name="user" method="post" action="Registration.htm">
      <form:hidden path="id"/>
      <table>
        <tr>
          <th>Email:</th>
          <td>
            <form:input path="username"/> <br/><form:errors path="username"/>
          </td>
        </tr>
        <tr>
          <th>Password:</th>
          <td>
            <form:password path="password" /> <br/><form:errors path="password"/>
          </td>
        </tr>
        <tr>
          <th>Password confirmation:</th>
          <td>
            <form:password path="passwordConfirmation" /><br/><form:errors path="passwordConfirmation"/>
          </td>
        </tr>
        <tr>
          <th>First name:</th>
          <td>
            <form:input path="firstName"/> <br/><form:errors path="firstName"/>
          </td>
        </tr>
        <tr>
          <th>Last Name:</th>
          <td>
            <form:input path="lastName"/> <br/><form:errors path="lastName"/>
          </td>
        </tr>
        <tr>
          <th>Mobile phone:</th>
          <td>
            <form:input path="phoneNumber"/> <br/><form:errors path="phoneNumber"/>
          </td>
        </tr>
        <tr>
          <td colspan="2">
            <input type="submit" name="save" value="Save">
          </td>
        </tr>
      </table>
    </form:form>
  </div>
</div>
