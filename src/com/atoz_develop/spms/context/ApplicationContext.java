package com.atoz_develop.spms.context;

import com.atoz_develop.spms.annotation.Component;
import org.reflections.Reflections;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

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

        prepareObjects(props);      // propertiesPath의 프로퍼티 파일에 정의된 객체 생성
        prepareAnnotationObjects(); // @Component가 붙은 클래스 객체 생성
        injectDependency();         // 의존성 주입
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

    /**
     * classpath를 뒤져서 Component 어노테이션이 지정된 클래스를 찾은뒤 해당 클래스의 객체를 준비한다.
     * 생성된 객체는 객체 테이블에 담는다.
     * 오픈소스 라이브러리 Reflections 사용: 자바 리플랙션 API보다 사용하기 쉽고 편함
     * @throws Exception
     */
    private void prepareAnnotationObjects() throws Exception {
        // Reflections 클래스는 원하는 클래스를 찾기 위해 사용
        // 파라미터값은 클래스를 찾을때 출발 패키지
        // "" -> classpath 모든 패키지 검색
        Reflections reflector = new Reflections("");
        // getTypesAnnotatedWith():
        // 파라미터값으로 넘긴 어노테이션이 붙은 클래스를 찾는다.
        // 반환값: @Component 어노테이션이 선언된 클래스 목록
        Set<Class<?>> list = reflector.getTypesAnnotatedWith(Component.class);
        String key = null;
        for(Class<?> clazz: list) {
            // getAnnotation(): 클래스로부터 어노테이션 추출
            key = clazz.getAnnotation(Component.class).value();
            objTable.put(key, clazz.getDeclaredConstructor().newInstance());
//            System.out.println(clazz.getName() + "(" + key + ") -> 객체 준비 완료");
        }
    }
}
