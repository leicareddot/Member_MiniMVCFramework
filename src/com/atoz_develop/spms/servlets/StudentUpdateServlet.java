package com.atoz_develop.spms.servlets;

import com.atoz_develop.spms.dao.StudentDao;
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
 * 학생 상세 정보 조회 및 수정
 */
@WebServlet("/student/update")
public class StudentUpdateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ServletContext sc = this.getServletContext();

            req.setAttribute("student", ((StudentDao) sc.getAttribute("studentDao")).selectOne(req.getParameter("student_no")));
            req.setAttribute("viewUrl", "/student/StudentUpdateForm.jsp");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext sc = this.getServletContext();

        try {
            int result = ((StudentDao) sc.getAttribute("studentDao")).update((Student) req.getAttribute("student"));

            if(result == 1)
                req.setAttribute("viewUrl", "redirect:list.do");
            else
                throw new SQLException();
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
