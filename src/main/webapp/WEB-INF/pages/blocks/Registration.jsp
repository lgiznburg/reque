<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<h2>Регистрация</h2>

<form:form commandName="userToReg" name="user" method="post" action="Registration.htm">
  <form:hidden path="id"/>

  <div class="form-group row">
    <form:label path="username" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">E-mail <sup>*</sup></form:label>
    <div class="col-sm-5">
      <form:input path="username"  class="form-control"/>
      <form:errors path="username" element="span" cssClass="text-danger" />
    </div>
  </div>
  <div class="form-group row">
    <form:label path="password" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Пароль <sup>*</sup></form:label>
    <div class="col-sm-5">
      <form:password path="password" class="form-control" />
      <form:errors path="password" element="span" cssClass="text-danger" />
    </div>
  </div>
  <div class="form-group row">
    <form:label path="passwordConfirmation" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Подтверждение пароля <sup>*</sup></form:label>
    <div class="col-sm-5">
      <form:password path="passwordConfirmation" class="form-control" />
      <form:errors path="passwordConfirmation" element="span" cssClass="text-danger" />
    </div>
  </div>
  <div class="form-group row">
    <form:label path="firstName" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Имя <sup>*</sup></form:label>
    <div class="col-sm-5">
      <form:input path="firstName" class="form-control" />
      <form:errors path="firstName" element="span" cssClass="text-danger" />
    </div>
  </div>
  <div class="form-group row">
    <form:label path="lastName" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Фамилия <sup>*</sup></form:label>
    <div class="col-sm-5">
      <form:input path="lastName" class="form-control" />
      <form:errors path="lastName" element="span" cssClass="text-danger" />
    </div>
  </div>
  <div class="form-group row">
    <form:label path="phoneNumber" cssClass="col-sm-2 col-form-label" cssErrorClass="col-sm-2 col-form-label text-danger">Мобильный телефон </form:label>
    <div class="col-sm-5">
      <form:input path="phoneNumber" class="form-control" />
      <form:errors path="phoneNumber" element="span" cssClass="text-danger" />
    </div>
  </div>
  <div class="row">
    <div class="col-sm-5 offset-2 small text-info">
      <p>Мы подтверждаем, что Ваши данные будут использованы только в целях организации предварительной записи для подачи
      заявления в приемную комиссию РНИМУ им. Н.И.Пирогова. Никаких рекламмых сообщений рассылаться не будет. Третьим лицам
      или организациям данные не передаются.</p>
      <p>Настоящим Вы подтверждаете правильность Ваших данных, а также соглашаетесь получать сообщения о дате и времени
      предварительной записи. Если Вы указали номер Вашего мобильного телефона, то Вы соглашаетесь получать информационные СМС.
      Согласие действует на время проведения приемной компании.</p>
    </div>
  </div>

  <div class="form-group row">
    <div class="col-sm-5 offset-2">
      <a class="btn btn-outline-success" href="<c:url value="/home.htm"/>">Назад</a>
      <button type="submit" class="btn btn-primary">Сохранить</button>
    </div>
  </div>
</form:form>
