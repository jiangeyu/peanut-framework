package com.peanut.framework;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:52 2018/7/13
 * @desc 类扫描器
 */
public interface ClassScanner {

    /**
     * 获取指定包名中的所有类
     *
     * @param packageName
     * @return
     */
    List<Class<?>> getClassList(String packageName);

    /**
     * 获取指定包名中指定注解的相关类
     *
     * @param packageName
     * @param annotationClass
     * @return
     */
    List<Class<?>> getClassListByAnnotation(String packageName, Class<? extends Annotation> annotationClass);

    /**
     * 获取指定包名中父类或接口的相关类
     *
     * @param packageName
     * @param superClass
     * @return
     */
    List<Class<?>> getClassBySuper(String packageName, Class<?> superClass);

}
