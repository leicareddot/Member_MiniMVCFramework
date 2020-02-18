package com.atoz_develop.spms.controls;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 로그아웃
 */
public class LogOutController implements Controller {
    @Override
    public String execute(Map<String, Object> model) throws Exception {
        HttpSession session = (HttpSession) model.get("session");
        session.invalidate();

        return "redirect:/auth/login.do";
    }
}
