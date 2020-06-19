package com.wjz.aop.aspectj.core;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SentinelResource {

    String value() default "";

    String blockHandler() default "";

    Class<?>[] blockHandlerClass() default {};
}
