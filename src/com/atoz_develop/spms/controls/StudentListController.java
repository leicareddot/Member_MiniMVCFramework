package com.atoz_develop.spms.controls;

import com.atoz_develop.spms.dao.StudentDao;

import java.util.Map;

/**
 * 학생 목록 조회
 */
public class StudentListController implements Controller {
    StudentDao studentDao;

    /**
     * StudentDao를 주입받기 위한 setter()
     * @param studentDao
     * @return StudentListController
     */
    public StudentListController setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
        return this;
    }

    @Override
    public String execute(Map<String, Object> model) throws Exception {
        model.put("students", studentDao.selectList());

        return "/student/StudentList.jsp";
    }
}
