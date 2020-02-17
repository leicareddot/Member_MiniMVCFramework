package com.atoz_develop.spms.servlets;

import com.atoz_develop.spms.dao.StudentDao;
import com.atoz_develop.spms.vo.Student;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/student/update")
public class StudentUpdateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ServletContext sc = this.getServletContext();

            req.setAttribute("student", ((StudentDao) sc.getAttribute("studentDao")).selectOne(req.getParameter("student_no")));
            RequestDispatcher rd = req.getRequestDispatcher("StudentUpdateForm.jsp");
            rd.forward(req, resp);
        } catch (SQLException e) {
            req.setAttribute("error", e);
            RequestDispatcher rd = req.getRequestDispatcher("/Error.jsp");
            rd.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext sc = this.getServletContext();

        try {
            int result = ((StudentDao) sc.getAttribute("studentDao")).update(new Student()
                .setDepartment(req.getParameter("department"))
                    .setStudentName(req.getParameter("student_name"))
                    .setPhoneNumber(req.getParameter("phone_number"))
                    .setAddress(req.getParameter("address"))
                    .setStudentNo(req.getParameter("student_no"))
            );

            if(result == 1)
                resp.sendRedirect("list");
            else
                throw new SQLException();
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
