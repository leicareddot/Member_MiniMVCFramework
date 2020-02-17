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

@WebServlet("/student/list")
public class StudentListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ServletContext sc = this.getServletContext();
            req.setAttribute("students", ((StudentDao) sc.getAttribute("studentDao")).selectList());

            resp.setContentType("text/html; charset=UTF-8");
            RequestDispatcher rd = req.getRequestDispatcher(
                    "/student/StudentList.jsp"
            );
            rd.include(req, resp);
        } catch (SQLException e) {
            req.setAttribute("error", e);
            RequestDispatcher rd = req.getRequestDispatcher("/Error.jsp");
            rd.forward(req, resp);
        }
    }
}
