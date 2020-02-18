package com.atoz_develop.spms.listeners;

import com.atoz_develop.spms.controls.*;
import com.atoz_develop.spms.dao.MySqlStudentDao;

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
            MySqlStudentDao studentDao = new MySqlStudentDao();
            studentDao.setDataSource(ds);

            // PageController 객체를 생성하고 StudentDao를 주입하여 ServletContext에 저장
            sc.setAttribute("/auth/login.do", new LogInController().setStudentDao(studentDao));
            sc.setAttribute("/auth/logout.do", new LogOutController());
            sc.setAttribute("/student/list.do", new StudentListController().setStudentDao(studentDao));
            sc.setAttribute("/student/add.do", new StudentAddController().setStudentDao(studentDao));
            sc.setAttribute("/student/update.do", new StudentUpdateController().setStudentDao(studentDao));
            sc.setAttribute("/student/delete.do", new StudentDeleteController().setStudentDao(studentDao));
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
