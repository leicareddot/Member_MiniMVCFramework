<%@ page import="com.atoz_develop.spms.vo.Student" %><%--
  Created by IntelliJ IDEA.
  User: Yoon
  Date: 2020-02-12
  Time: 오전 3:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<jsp:useBean id="student" scope="session" class="com.atoz_develop.spms.vo.Student" />--%>
<%
    Student student = (Student) session.getAttribute("student");
%>
<div style="background-color: darkcyan; color: aliceblue; height: 20px; padding: 5px;">
    SPMS
    <% if(student == null) { %>
    <span style="float: right;">
        <a style="color: white;" href="<%=request.getContextPath()%>/auth/login">로그인</a>
    </span>
    <% } else {%>
    <span style="float: right;"><%=student.getStudentName()%>
        <a style="color: white;" href="<%=request.getContextPath()%>/auth/logout">로그아웃</a>
    </span>
    <% } %>
</div>