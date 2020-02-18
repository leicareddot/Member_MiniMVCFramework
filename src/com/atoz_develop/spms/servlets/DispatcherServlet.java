package com.atoz_develop.spms.servlets;

import com.atoz_develop.spms.controls.*;
import com.atoz_develop.spms.vo.Student;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Front Controller
 * 배치 URL을 "*.do"로 지정하여 클라이언트의 요청 중 서블릿 경로 이름이 .do로 끝나는 경우 이 서블릿이 처리하도록 함
 * <p>
 * 1. 클라이언트의 요청을 알맞은 PageController에게 전달
 * 2. PageController가 필요한 데이터 준비
 * 3. PageController의 결과 데이터를 JSP에서 사용할 수 있도록 서블릿 보관소에 저장
 * 4. Exception 처리
 */
@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {

    /*
    참고: Servlet 인터페이스의 service()가 아니므로 서블릿 컨테이너가 직접 호출하지 않음
    서블릿 컨테이너는 service(ServletRequest, ServletResponse)를 호출하고
    이 메소드가 service(HttpServletRequest, HttpSerlvetResponse)를 호출하는 구조
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        // 요청 서블릿 URL
        String servletPath = req.getServletPath();

        try {
            ServletContext sc = this.getServletContext();
            // FrontController - PageController 데이터 전달을 위한 Map 객체
            Map<String, Object> model = new HashMap<>();

            // ServletContext에 저장돼있는 PageController 사용
            Controller pageController = (Controller) sc.getAttribute(servletPath);

            // 서블릿 URL에 따라 PageController 지정 -s
            if ("/student/add.do".equals(servletPath)) {
                if (req.getParameter("student_no") != null) {
                    // 매개변수값을 꺼내서 Map에 담음
                    model.put("student", new Student()
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
                if (req.getParameter("student_no") != null) {
                    // 매개변수값을 꺼내서 Map에 담음
                    model.put("student", new Student()
                            .setDepartment(req.getParameter("department"))
                            .setStudentName(req.getParameter("student_name"))
                            .setPhoneNumber(req.getParameter("phone_number"))
                            .setAddress(req.getParameter("address"))
                            .setStudentNo(req.getParameter("student_no")));
                }
            } else if ("/student/delete.do".equals(servletPath)) {
                if (req.getParameter("student_no") != null) {
                    model.put("studentNo", req.getParameter("student_no"));
                }
            } else if ("/auth/login.do".equals(servletPath)) {
                if (req.getParameter("student_no") != null) {
                    model.put("studentNo", req.getParameter("student_no"));
                    model.put("password", req.getParameter("password"));
                    model.put("session", req.getSession());
                }
            } else if ("/auth/logout.do".equals(servletPath)) {
                pageController = new LogOutController();
                model.put("session", req.getSession());
            }
            // 서블릿 URL에 따라 PageController 지정 -e

            // PageController에 실행 요청
            String viewUrl = pageController.execute(model);

            // PageController 실행 완료 후 결과 데이터를 JSP가 사용할 수 있도록 ServletRequest에 보관
            for (String key : model.keySet()) {
                req.setAttribute(key, model.get(key));
            }

            // 화면 출력을 위해 ServletRequest에 보관된 뷰 URL로 실행 위임 -s
            if (viewUrl.startsWith("redirect:")) {   // redirect
                resp.sendRedirect(sc.getContextPath() + viewUrl.substring(9));

                return;
            } else {    // include
                RequestDispatcher rd = req.getRequestDispatcher(viewUrl);
                rd.include(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", e);
            RequestDispatcher rd = req.getRequestDispatcher("/Error.jsp");
            rd.forward(req, resp);
        }
    }
}
