package com.atoz_develop.spms.servlets;

import com.atoz_develop.spms.vo.Student;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Front Controller
 * 배치 URL을 "*.do"로 지정하여 클라이언트의 요청 중 서블릿 경로 이름이 .do로 끝나는 경우 이 서블릿이 처리하도록 함
 *
 * 1. 클라이언트의 요청을 알맞은 PageController에게 전달
 * 2. PageController가 필요한 데이터 준비
 * 3. Exception 처리
 */
@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {

    /*
    Servlet 인터페이스의 service()가 아니므로 서블릿 컨테이너가 직접 호출하지 않음
    서블릿 컨테이너는 service(ServletRequest, ServletResponse)를 호출하고
    이 메소드가 service(HttpServletRequest, HttpSerlvetResponse)를 호출하는 구조
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        // 요청 서블릿 URL
        String servletPath = req.getServletPath();

        try {
            String pageControllerPath = null;

            // 서블릿 URL에 따라 PageController 지정 -s
            if ("/student/list.do".equals(servletPath)) {
                pageControllerPath = "/student/list";
            } else if ("/student/add.do".equals(servletPath)) {
                pageControllerPath = "/student/add";
                if (req.getParameter("student_no") != null) {
                    // 매개변수값을 꺼내서 VO에 담고 ServletRequest에 보관
                    req.setAttribute("student", new Student()
                            .setStudentNo(req.getParameter("student_no"))
                            .setStudentName(req.getParameter("student_name"))
                            .setAddress(req.getParameter("address"))
                            .setAge(Integer.parseInt(req.getParameter("age")))
                            .setDepartment(req.getParameter("department"))
                            .setGender(req.getParameter("gender"))
                            .setGrade(Integer.parseInt(req.getParameter("grade")))
                            .setPassword(req.getParameter("password"))
                            .setPhoneNumber(req.getParameter("phone_number"))
                    );
                }
            } else if ("/student/update.do".equals(servletPath)) {
                pageControllerPath = "/student/update";
                if (req.getParameter("student_no") != null) {
                    // for test
                    /*Student student = new Student();
                    student.setDepartment(req.getParameter("department"))
                            .setStudentName(req.getParameter("student_name"))
                            .setPhoneNumber(req.getParameter("phone_number"))
                            .setAddress(req.getParameter("address"))
                            .setStudentNo(req.getParameter("student_no"));
                    System.out.println(student);
                    req.setAttribute("student", student);*/

                    // 매개변수값을 꺼내서 VO에 담고 ServletRequest에 보관
                    req.setAttribute("student", new Student()
                            .setDepartment(req.getParameter("department"))
                            .setStudentName(req.getParameter("student_name"))
                            .setPhoneNumber(req.getParameter("phone_number"))
                            .setAddress(req.getParameter("address"))
                            .setStudentNo(req.getParameter("student_no")));
                }
            } else if("/student/delete.do".equals(servletPath)) {
                pageControllerPath = "/student/delete";
            } else if("/auth/login.do".equals(servletPath)) {
                pageControllerPath = "/auth/login";
            } else if("/auth/logout.do".equals(servletPath)) {
                pageControllerPath = "/auth/logout";
            }
            // 서블릿 URL에 따라 PageController 지정 -e

            // 요청을 처리할 PageController에 실행 위임
            RequestDispatcher rd = req.getRequestDispatcher(pageControllerPath);
            rd.include(req, resp);
            // ~~~ PageController 실행 완료 ~~~

            // 화면 출력을 위해 ServletRequest에 보관된 뷰 URL로 실행 위임 -s
            String viewUrl = (String) req.getAttribute("viewUrl");
            if(viewUrl.startsWith("redirect:")) {
                resp.sendRedirect(viewUrl.substring(9));
                return;
            } else {
                rd = req.getRequestDispatcher(viewUrl);
                rd.include(req, resp);
            }
            // 화면 출력을 위해 ServletRequest에 보관된 뷰 URL로 실행 위임 -e
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", e);
            RequestDispatcher rd = req.getRequestDispatcher("/Error.jsp");
            rd.forward(req, resp);
        }
    }
}
