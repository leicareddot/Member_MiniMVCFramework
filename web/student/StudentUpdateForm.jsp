<%--
  Created by IntelliJ IDEA.
  User: Yoon
  Date: 2020-02-13
  Time: 오전 5:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>학생 정보</title>
</head>
<body>
<h1>학생정보</h1>
<form action='update.do' method='post'>
    학번: <input type='text' name='studentNo' value='${requestScope.student.studentNo}' readonly><br>
    학과: <input type='text' name='department' value='${requestScope.student.department}'><br>
    이름: <input type='text' name='studentName' value='${requestScope.student.studentName}'><br>
    학년: <input type='text' name='grade' value='${requestScope.student.grade}' readonly><br>
    성별: <input type='text' name='gender' value='${requestScope.student.gender}' readonly><br>
    나이: <input type='text' name='age' value='${requestScope.student.age}' readonly><br>
    전화번호: <input type='text' name='phoneNumber' value='${requestScope.student.phoneNumber}'><br>
    주소: <input type='text' name='address' value='${requestScope.student.address}'><br>
    <input type='submit' value='수정'>
    <c:if test="${sessionScope.student.studentNo == 'admin'}">
        <input type="button" value="삭제" onClick="location.href='delete.do?studentNo=${student.studentNo}'"
           style="display: ${'admin' eq sessionScope.student.studentNo ? inline-block : none}">
    </c:if>
    <input type='button' value='취소' onClick='location.href="list.do"'>
</form>
</body>
</html>
