package com.atoz_develop.spms.controls;

import com.atoz_develop.spms.dao.StudentDao;

import java.util.Map;

public class StudentListController implements Controller {
    @Override
    public String execute(Map<String, Object> model) throws Exception {
        StudentDao studentDao = (StudentDao) model.get("studentDao");
        model.put("students", studentDao.selectList());

        return "/student/StudentList.jsp";
    }
}
