<%--
  Created by IntelliJ IDEA.
  User: leonid
  Date: 17.12.17
  Time: 21:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<html>
<head>
  <title>${title}</title>
</head>
<body>
<div id="header">
  <div id="menu">

  </div>
</div>
<div id="content">
  <c:import url="${content}"/>
</div>

</body>
</html>
