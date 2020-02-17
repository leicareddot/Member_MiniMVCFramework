<%--
  Created by IntelliJ IDEA.
  User: Yoon
  Date: 2020-02-13
  Time: 오전 4:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String studentName = (String) request.getAttribute("studentName");
%>
<html>
<head>
    <meta http-equiv="Refresh" content="3; url=list">
    <title>가입 완료</title>
</head>
<body>
<p><%=studentName%>님, 신규 학생 등록이 완료되었습니다.<br/>
    잠시 후 학생 목록 화면으로 이동합니다.</p>
</body>
</html>
