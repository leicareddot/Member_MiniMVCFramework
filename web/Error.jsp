<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>시스템 오류</title>
</head>
<body>
요청을 처리하는 중에 문제가 발생하였습니다. 잠시 후에 다시 요청하시기 바랍니다.<br>
<%
    Exception e = (Exception)request.getAttribute("error");
%>
<%=e.toString()%>
</body>
</html>
