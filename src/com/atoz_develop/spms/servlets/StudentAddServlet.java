package com.atoz_develop.spms.servlets;

import com.atoz_develop.spms.dao.MySqlStudentDao;
import com.atoz_develop.spms.vo.Student;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 신규 학생 등록
 */
@WebServlet("/student/add")
public class StudentAddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("viewUrl", "/join/JoinForm.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext sc = this.getServletContext();

        try {
            // (Student) req.getAttribute("student") : FrontController가 저장해둔 Student 객체 사용
            int result = ((MySqlStudentDao) sc.getAttribute("studentDao")).insert((Student) req.getAttribute("student"));

            if(result == 1) {
                req.setAttribute("viewUrl", "redirect:list.do");
            } else {
                throw new SQLException();
            }

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}