package com.atoz_develop.spms.listeners;

import com.atoz_develop.spms.dao.StudentDao;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class ContextLoaderListener implements ServletContextListener {

    /**
     * 공용 자원 세팅
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();

        try {
            // 톰캣 서버에서 자원을 찾기 위해 InitialContext 인스턴스 생성
            InitialContext initialContext = new InitialContext();
            // lookup() 메소드로 JNDI 이름으로 등록돼있는 서버 자원 찾음
            // @name : 서버 자원의 JNDI 이름
            // 찾으려는 자원이 JDBC DataSource이므로 java:comp/env...
            DataSource ds = (DataSource) initialContext.lookup(
                    "java:comp/env/jdbc/knou"
            );

            // Dao 준비
            StudentDao studentDao = new StudentDao();
            studentDao.setDataSource(ds);

            // Dao 저장
            sc.setAttribute("studentDao", studentDao);
        } catch (NamingException e) {
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
