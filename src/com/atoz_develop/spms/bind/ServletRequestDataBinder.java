package com.atoz_develop.spms.bind;

import javax.servlet.ServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Set;

public class ServletRequestDataBinder {
    /**
     * 요청 파라미터 값을 자바 객체에 담아서 반환한다.
     * Front controller에서 호출
     * @param request 요청 파라미터 값
     * @param dataType 데이터 타입
     * @param dataName 데이터 이름
     * @return 데이터 객체
     * @throws Exception
     */
    public static Object bind(ServletRequest request, Class<?> dataType, String dataName) throws Exception {
        // 데이터 타입이 primitive type인지 확인
        if(isPrimitiveType(dataType)) {
            // primitive type이면 즉시 객체를 생성해서 반환
            return createValueObject(dataType, request.getParameter(dataName));
        }

        // Primitive type이 아니면~
        // 요청 파라미터명
        Set<String> paramNames = request.getParameterMap().keySet();

        // 값을 저장할 객체 생성
//        Object dataObject = dataType.newInstance();   //deprecated
        Object dataObject = dataType.getDeclaredConstructor().newInstance();
        Method m = null;

        // 요청 파라미터 개수만큼 반복하면서 데이터 객체에 값을 할당
        for(String paramName: paramNames) {
            // 파라미터명과 일치하는 setter() 찾음
            m = findSetter(dataType, paramName);
            if(m != null) {
                // setter()를 찾았으면 dataObject에 대해 호출
                // setter() 호출 시 요청 파라미터의 값을 형식에 맞춰 넘김
                // invoke() : 메소드 호출
                // getParameterTypes() : 메소드의 매개변수 목록을 배열로 반환
                m.invoke(dataObject, createValueObject(m.getParameterTypes()[0], request.getParameter(paramName)));
            }
        }

        return dataObject;
    }

    /**
     * type이 primitive type인지 확인
     * @param type
     * @return int, long, float, double, boolean, java.util.Date, java.lang.String 타입이면 true, 아니면 false
     * byte, short는 필요 시 추가
     */
    private static boolean isPrimitiveType(Class<?> type) {
        if(type.getName().equals("int") || type == Integer.class ||
        type.getName().equals("long") || type == Long.class ||
        type.getName().equals("float") || type == Float.class ||
        type.getName().equals("double") || type == Double.class ||
        type.getName().equals("boolean") || type == Boolean.class ||
        type == Date.class || type == String.class) {
            return true;
        }

        return false;
    }

    /**
     * Primitive type의 객체를 생성
     * @param type
     * @param value
     * @return
     */
    private static Object createValueObject(Class<?> type, String value) {
        if(type.getName().equals("int") || type == Integer.class) {
            return Integer.valueOf(value);
        } else if(type.getName().equals("long") || type == Long.class) {
            return Long.valueOf(value);
        } else if(type.getName().equals("float") || type == Float.class) {
            return Float.valueOf(value);
        } else if(type.getName().equals("double") || type == Double.class) {
            return Double.valueOf(value);
        } else if(type.getName().equals("boolean") || type == Boolean.class) {
            return Boolean.valueOf(value);
        } else if(type == Date.class) {
            return java.sql.Date.valueOf(value);
        } else {
            return value;
        }
    }

    /**
     * 클래스(type)을 조사해서 주어진 이름(name))과 일치하는 setter()를 찾는다.
     * @param type
     * @param name
     * @return
     */
    private static Method findSetter(Class<?> type, String name) {
        // 데이터 타입에서 메소드 목록 얻음
        // getMethods() : 클래스에 선언된 모든 public 메소드 목록을 배열로 반환
        Method[] methods = type.getMethods();

        String propName = null;

        // 메소드 목록을 반복하여 setter()에 대해서만 작업 수행
        for(Method m: methods) {
            if(!m.getName().startsWith("set")) continue;
            // setter()일 경우
            // 메소드 이름에서 'set' 제외
            propName = m.getName().substring(3);
            // 요청 파라미터 이름과 일치하는지 확인
            if(propName.toLowerCase().equals(name.toLowerCase())) {
                //일치하는 setter()를 찾으면 반환
                return m;
            }
        }

        return null;
    }
}
