package com.atoz_develop.spms.controls;

import com.atoz_develop.spms.annotation.Component;
import com.atoz_develop.spms.bind.DataBinding;
import com.atoz_develop.spms.dao.MySqlStudentDao;
import com.atoz_develop.spms.vo.Student;

import java.util.Map;

/**
 * 신규 학생 등록
 */
@Component("/student/add.do")
public class StudentAddController implements Controller, DataBinding {
    MySqlStudentDao studentDao;

    /**
     * StudentDao를 주입받기 위한 setter()
     * @param studentDao
     * @return StudentAddController
     */
    public StudentAddController setStudentDao(MySqlStudentDao studentDao) {
        this.studentDao = studentDao;
        return this;
    }

    /**
     * 필요한 데이터 정의
     * @return 데이터의 이름과 타입 정보를 담은 Object[]
     */
    @Override
    public Object[] getDataBinders() {
        return new Object[]{"student", com.atoz_develop.spms.vo.Student.class};
    }

    @Override
    public String execute(Map<String, Object> model) throws Exception {
        Student student = (Student) model.get("student");

        if(student.getStudentNo() == null) {      // Form 요청 시
            return "/join/JoinForm.jsp";
        } else {                                // 회원 등록 요청 시
            studentDao.insert(student);
            return "redirect:/student/list.do";
        }
    }
}
