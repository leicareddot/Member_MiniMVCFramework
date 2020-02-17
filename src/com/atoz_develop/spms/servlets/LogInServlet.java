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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/auth/login")
public class LogInServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // LogInForm.jsp로 포워딩
        RequestDispatcher rd = req.getRequestDispatcher("/auth/LogInForm.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext sc = req.getServletContext();

        try {
            Student student = ((StudentDao) sc.getAttribute("studentDao")).exist(req.getParameter("student_no"), req.getParameter("password"));
            if (student != null) {
                // HttpSession에 저장
                HttpSession session = req.getSession();
                session.setAttribute("student", student);

                // /student/list로 리다이렉트
                resp.sendRedirect("../student/list");
            } else {
                // 로그인 실패 시 /auth/LogInFail.jsp로 포워딩
                RequestDispatcher rd = req.getRequestDispatcher("/auth/LogInFail.jsp");
                rd.forward(req, resp);
            }
        } catch (SQLException e) {
            req.setAttribute("error", e);
            RequestDispatcher rd = req.getRequestDispatcher("/Error.jsp");
            rd.forward(req, resp);
        }
    }
}
