package com.atoz_develop.spms.controls;

import com.atoz_develop.spms.annotation.Component;
import com.atoz_develop.spms.bind.DataBinding;
import com.atoz_develop.spms.dao.MySqlStudentDao;
import com.atoz_develop.spms.vo.Student;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Component("/auth/login.do")
public class LogInController implements Controller, DataBinding {
    MySqlStudentDao studentDao;

    /**
     * StudentDao를 주입받기 위한 setter()
     * @param studentDao
     * @return LogInController
     */
    public LogInController setStudentDao(MySqlStudentDao studentDao) {
        this.studentDao = studentDao;
        return this;
    }

    /**
     * 필요한 데이터 정의
     * @return
     */
    @Override
    public Object[] getDataBinders() {
        return new Object[]{"studentNo", String.class, "password", String.class};
    }

    @Override
    public String execute(Map<String, Object> model) throws Exception {
        String studentNo = (String) model.get("studentNo");
        String password = (String) model.get("password");

        if(studentNo == null) {    // Form 조회
            return "/auth/LogInForm.jsp";
        } else {    // 로그인
            Student student = studentDao.exist(studentNo, password);
            if(student == null) {
                return "/auth/LogInFail.jsp";
            } else {
                ((HttpSession) model.get("session")).setAttribute("student", student);

                return "redirect:/student/list.do";
            }
        }
    }
}
