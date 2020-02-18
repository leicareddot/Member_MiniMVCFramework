package com.atoz_develop.spms.controls;

import com.atoz_develop.spms.dao.StudentDao;
import com.atoz_develop.spms.vo.Student;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class LogInController implements Controller {
    @Override
    public String execute(Map<String, Object> model) throws Exception {
        String studentNo = (String) model.get("studentNo");
        String password = (String) model.get("password");

        if(studentNo == null) {    // Form 조회
            return "/auth/LogInForm.jsp";
        } else {    // 로그인
            Student student = ((StudentDao) model.get("studentDao")).exist(studentNo, password);
            if(student == null) {
                return "/auth/LogInFail.jsp";
            } else {
                ((HttpSession) model.get("session")).setAttribute("student", student);

                return "redirect:/student/list.do";
            }
        }
    }
}
