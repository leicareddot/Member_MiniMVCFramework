package com.atoz_develop.spms.bind;

/**
 * 요청 파라미터 데이터가 필요한 controller는 이 인터페이스를 구현한다.
 */
public interface DataBinding {
    /**
     * 필요한 데이터 정의
     * @return 데이터의 이름과 타입 정보를 담은 Object[]
     * Ex) new Object[]{"데이터이름", 데이터타입, "데이터이름", 데이터타입, ...}
     */
    Object[] getDataBinders();
}
