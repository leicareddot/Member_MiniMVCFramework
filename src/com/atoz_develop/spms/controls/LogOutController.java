package com.atoz_develop.spms.controls;

import com.atoz_develop.spms.annotation.Component;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 로그아웃
 */
@Component("/auth/logout.do")
public class LogOutController implements Controller {
    @Override
    public String execute(Map<String, Object> model) throws Exception {
        HttpSession session = (HttpSession) model.get("session");
        session.invalidate();

        return "redirect:/auth/login.do";
    }
}
