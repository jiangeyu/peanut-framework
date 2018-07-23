package com.peanut.framework.annotation;

import java.lang.annotation.*;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:43 2018/7/13
 * @desc 定义切面类
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    String pkg() default "";

    String cls() default "";

    Class<? extends Annotation> annotion() default Aspect.class;
}
