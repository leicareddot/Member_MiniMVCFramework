<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>학생 목록</title>
</head>
<body>
<jsp:include page="/Header.jsp"/>
<h1>학생 목록</h1>
<p><a href="add.do">신규 학생</a></p>
<c:forEach var="student" items="${students}">
    <c:if test="${sessionScope.student.studentNo == 'admin'}">
        <input type="button" value="삭제" onClick="location.href='delete.do?student_no=${student.studentNo}'" />
    </c:if>
    ${student.studentNo},
    ${student.department},
    <a href="update.do?student_no=${student.studentNo}">${student.studentName}</a>,
    ${student.grade},
    ${student.gender},
    ${student.age},
    ${student.phoneNumber},
    ${student.address}<br>
</c:forEach>
<jsp:include page="/Tail.jsp"/>
</body>
</html>
