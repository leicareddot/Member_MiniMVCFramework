package com.atoz_develop.spms.controls;

import java.util.Map;

public interface Controller {
    /**
     * FrontController가 PageController에게 작업을 위임하기 위해 호출하는 메소드
     * @param model
     * @return 화면 출력을 수행하는 JSP URL
     * @throws Exception
     */
    String execute(Map<String, Object> model) throws Exception;
}
