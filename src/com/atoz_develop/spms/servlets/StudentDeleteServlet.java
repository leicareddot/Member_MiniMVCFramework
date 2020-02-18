package com.atoz_develop.spms.servlets;

import com.atoz_develop.spms.dao.StudentDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 학생 삭제
 */
@WebServlet("/student/delete")
public class StudentDeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext sc = req.getServletContext();

        try {
            int result = ((StudentDao) sc.getAttribute("studentDao")).delete(req.getParameter("student_no"));
            if(result > 0) req.setAttribute("viewUrl", "redirect:list.do");
            else throw new SQLException();
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}