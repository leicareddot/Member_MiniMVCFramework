package com.atoz_develop.spms.servlets;

import com.atoz_develop.spms.dao.StudentDao;
import com.atoz_develop.spms.vo.Student;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 로그인
 */
@WebServlet("/auth/login")
public class LogInServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Redirect
        // http://localhost:8080/Member_MiniMVCFramework/auth/LogInForm.jsp
//        req.setAttribute("viewUrl", "redirect:LogInForm.jsp");

        // Include
        req.setAttribute("viewUrl", "LogInForm.jsp");
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
                req.setAttribute("viewUrl", "redirect:../student/list.do");
            } else {
                // Redirect
//                req.setAttribute("viewUrl", "redirect:LogInFail.jsp");

                // Include
                req.setAttribute("viewUrl", "/auth/LogInFail.jsp");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
