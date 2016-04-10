package com.steve.creact.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author:YJJ
 * @date:2016/3/31
 * @email:yangjianjun@117go.com
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface DataBean {

    String beanName();

    int layout() default 0;

    Class<?> data();
}
