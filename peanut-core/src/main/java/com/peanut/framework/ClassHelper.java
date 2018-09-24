package com.peanut.framework;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:52 2018/7/13
 * @desc  根据条件获取相关类
 */
public class ClassHelper {

    private static final String basePackage = ConfigHelper.getString("com.github.peanut");

    private static final ClassScanner classScanner = InstanceFactory.getClassScanner();

    public static List<Class<?>> getClassList() {
        return classScanner.getClassList(basePackage);
    }

    public static List<Class<?>> getClassListBySuper(Class<?> superClass) {
        return classScanner.getClassBySuper(basePackage, superClass);
    }

    public static List<Class<?>> getClassListByAnnotation(Class<? extends Annotation> annotationClass) {
        return classScanner.getClassListByAnnotation(basePackage, annotationClass);
    }

}
