package com.atoz_develop.spms.controls;

import com.atoz_develop.spms.dao.StudentDao;

import java.util.Map;

/**
 * 학생 삭제
 */
public class StudentDeleteController implements Controller {
    @Override
    public String execute(Map<String, Object> model) throws Exception {
        ((StudentDao) model.get("studentDao")).delete((String) model.get("studentNo"));

        return "redirect:/student/list.do";
    }
}
