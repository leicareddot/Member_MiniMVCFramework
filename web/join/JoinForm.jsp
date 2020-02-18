<%--
  Created by IntelliJ IDEA.
  User: Yoon
  Date: 2020-02-13
  Time: 오전 4:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>신규 학생 등록</title>
</head>
<body>
<h1>신규 학생 등록</h1>
<form action='add.do' method='post'>
    학번 : <input type='text' name='student_no'><br>
    이름 : <input type='text' name='student_name'><br>
    비밀번호 : <input type='password' name='password'><br>
    성별 : <input type='text' name='gender'><br>
    학과 : <input type='text' name='department'><br>
    학년 : <input type='text' name='grade'><br>
    나이 : <input type='text' name='age'><br>
    전화번호 : <input type='text' name='phone_number'><br>
    주소 : <input type='text' name='address'><br>
    <input type='submit' value='등록'>
    <input type='reset' value='취소'>
</form>
</body>
</html>
