package com.symbio.dashboard.data.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(value = {ElementType.METHOD})
public @interface JPAUpdate {

    boolean includeNull() default true;
}
