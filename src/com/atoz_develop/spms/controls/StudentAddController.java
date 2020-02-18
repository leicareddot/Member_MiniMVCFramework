package com.atoz_develop.spms.controls;

import com.atoz_develop.spms.dao.StudentDao;
import com.atoz_develop.spms.vo.Student;

import java.util.Map;

/**
 * 신규 학생 등록
 */
public class StudentAddController implements Controller {
    @Override
    public String execute(Map<String, Object> model) throws Exception {
        if(model.get("student") == null) {      // Form 요청 시
            return "/join/JoinForm.jsp";
        } else {                                // 회원 등록 요청 시
            StudentDao studentDao = (StudentDao) model.get("studentDao");
            Student student = (Student) model.get("student");
            studentDao.insert(student);

            return "redirect:/student/list.do";
        }
    }
}
