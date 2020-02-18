package com.atoz_develop.spms.controls;

import com.atoz_develop.spms.dao.StudentDao;
import com.atoz_develop.spms.vo.Student;

import java.util.Map;

/**
 * 학생 정보 조회 및 수정
 */
public class StudentUpdateController implements Controller {
    @Override
    public String execute(Map<String, Object> model) throws Exception {
        Student student = (Student) model.get("student");
        StudentDao studentDao = (StudentDao) model.get("studentDao");

        if(student.getDepartment() == null) {  // 조회
            model.put("student", studentDao.selectOne(student.getStudentNo()));

            return "/student/StudentUpdateForm.jsp";
        } else {    // 수정
            studentDao.update((Student) model.get("student"));

            return "redirect:/student/list.do";
        }
    }
}
