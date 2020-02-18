package com.atoz_develop.spms.controls;

import com.atoz_develop.spms.dao.StudentDao;

import java.util.Map;

/**
 * 학생 삭제
 */
public class StudentDeleteController implements Controller {
    StudentDao studentDao;

    /**
     * StudentDao를 주입받기 위한 setter()
     * @param studentDao
     * @return StudentDeleteController
     */
    public StudentDeleteController setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
        return this;
    }

    @Override
    public String execute(Map<String, Object> model) throws Exception {
        studentDao.delete((String) model.get("studentNo"));

        return "redirect:/student/list.do";
    }
}
