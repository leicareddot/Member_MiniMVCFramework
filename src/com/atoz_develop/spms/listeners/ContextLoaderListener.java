package com.atoz_develop.spms.listeners;

import com.atoz_develop.spms.context.ApplicationContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextLoaderListener implements ServletContextListener {

    static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 공용 자원 세팅
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();

        try {
            // 프로퍼티 파일의 이름과 경로 정보를 web.xml에서 가져옴
            String propertiesPath = sc.getRealPath(sc.getInitParameter("contextConfigLocation"));
            applicationContext = new ApplicationContext(propertiesPath);

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 공용 자원 해제
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // 톰캣 서버에 해제 설정이 되어있으므로 구현이 필요 없음
    }
}
