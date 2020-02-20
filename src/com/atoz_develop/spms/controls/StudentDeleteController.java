package com.atoz_develop.spms.controls;

import com.atoz_develop.spms.annotation.Component;
import com.atoz_develop.spms.bind.DataBinding;
import com.atoz_develop.spms.dao.MySqlStudentDao;

import java.util.Map;

/**
 * 학생 삭제
 */
@Component("/student/delete.do")
public class StudentDeleteController implements Controller, DataBinding {
    MySqlStudentDao studentDao;

    /**
     * StudentDao를 주입받기 위한 setter()
     * @param studentDao
     * @return StudentDeleteController
     */
    public StudentDeleteController setStudentDao(MySqlStudentDao studentDao) {
        this.studentDao = studentDao;
        return this;
    }

    @Override
    public Object[] getDataBinders() {
        return new Object[]{"studentNo", String.class};
    }

    @Override
    public String execute(Map<String, Object> model) throws Exception {
        studentDao.delete((String) model.get("studentNo"));

        return "redirect:/student/list.do";
    }
}
