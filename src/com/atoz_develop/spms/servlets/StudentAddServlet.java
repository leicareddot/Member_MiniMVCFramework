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

@WebServlet("/student/add")
public class StudentAddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("../join/JoinForm.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext sc = this.getServletContext();

        try {
            int result = ((StudentDao) sc.getAttribute("studentDao")).insert(new Student()
                .setStudentNo(req.getParameter("student_no"))
                    .setDepartment(req.getParameter("department"))
                    .setStudentName(req.getParameter("student_name"))
                    .setGrade(Integer.parseInt(req.getParameter("grade")))
                    .setGender(req.getParameter("gender"))
                    .setAge(Integer.parseInt(req.getParameter("age")))
                    .setPhoneNumber(req.getParameter("phone_number"))
                    .setAddress(req.getParameter("address"))
                    .setPassword(req.getParameter("password"))
            );

            if(result == 1) {
                req.setAttribute("studentName", req.getParameter("student_name"));
                RequestDispatcher rd = req.getRequestDispatcher("../join/JoinSuccess.jsp");
                rd.forward(req, resp);
            } else {
                throw new SQLException();
            }

        } catch (SQLException e) {
            req.setAttribute("error", e);
            RequestDispatcher rd = req.getRequestDispatcher("/Error.jsp");
            rd.forward(req, resp);
        }
    }
}