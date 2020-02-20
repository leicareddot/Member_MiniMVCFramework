package com.atoz_develop.spms.context;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

public class ApplicationContext {

    // 만든 객체 보관용
    Map<String, Object> objTable = new Hashtable<>();

    // 객체를 꺼낼 getter
    public Object getBean(String key) {
        return objTable.get(key);
    }

    public ApplicationContext(String propertiesPath) throws Exception {
        // 프로퍼티 파일 로딩
        Properties props = new Properties();
        props.load(new FileReader(propertiesPath));

        prepareObjects(props);
        injectDependency();
    }

    /**
     * 프로퍼티에 따라 객체 준비
     * @param props
     * @throws Exception
     */
    private void prepareObjects(Properties props) throws Exception {
        Context context = new InitialContext(); // JNDI 객체를 찾기 위해
        String key = null;
        String value = null;

        // 프로퍼티를 꺼내 객체를 생성하고 저장(put)
        for (Object item: props.keySet()) {
            key = (String) item;
            value = props.getProperty(key);

            if (key.startsWith("jndi.")) {  // key가 jndi.로 시작하면 객체를 생성하지 않고 InitialContext를 통해 얻는다.
                objTable.put(key, context.lookup(value));
            } else {
                objTable.put(key, Class.forName(value).getDeclaredConstructor().newInstance());
            }
        }
    }

    /**
     * 각 객체가 필요로 하는 의존 객체 할당
     * @throws Exception
     */
    private void injectDependency() throws Exception {
        for (String key: objTable.keySet()) {
            if (!key.startsWith("jndi.")) {
                callSetter(objTable.get(key));  // Setter 호출
            }
        }
    }

    /**
     * 파라미터로 주어진 객체에 대해 setter 메소드를 찾아 호출
     * @param object setter 메소드를 찾을 객체
     * @throws Exception
     */
    private void callSetter(Object object) throws Exception {
        Object dependency = null;

        for (Method m: object.getClass().getMethods()) {
            if (m.getName().startsWith("set")) {    // Setter()를 찾아서
                dependency = findObjectByType(m.getParameterTypes()[0]);

                if (dependency != null) {   // Setter()를 호출
                    m.invoke(object, dependency);
                }
            }
        }
    }

    /**
     * Map에서 type에 맞는 의존 객체를 찾아 리턴
     * @param type
     * @return 의존 객체
     */
    private Object findObjectByType(Class<?> type) {
        for (Object obj: objTable.values()) {
            if (type.isInstance(obj)) {
                return obj;
            }
        }

        return null;
    }
}
