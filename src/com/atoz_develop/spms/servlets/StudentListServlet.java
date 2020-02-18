package com.atoz_develop.spms.servlets;

import com.atoz_develop.spms.dao.MySqlStudentDao;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 학생 목록 조회
 */
@WebServlet("/student/list")
public class StudentListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ServletContext sc = this.getServletContext();
            req.setAttribute("students", ((MySqlStudentDao) sc.getAttribute("studentDao")).selectList());

            // FrontController에게 알려줄 JSP URL 정보 저장
            req.setAttribute("viewUrl", "/student/StudentList.jsp");
        } catch (SQLException e) {
            // Dao 실행 중 예외 발생 시 ServletException 객체에 담아 던짐
            throw new ServletException(e);
        }
    }
}
