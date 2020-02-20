package com.atoz_develop.spms.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*어노테이션 유지 정책 - RetentionPolicy.RUNTIME:
* 클래스 파일에 기록됨
* 실행 시에 유지됨
* 실행 중 클래스에 기록된 어노테이션 값 참조 가능
* */
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {
    String value() default "";
}
