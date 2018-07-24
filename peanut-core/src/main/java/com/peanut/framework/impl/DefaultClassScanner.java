package com.peanut.framework.impl;

import com.peanut.framework.ClassScanner;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:27 2018/7/23
 * @desc
 */
public class DefaultClassScanner implements ClassScanner {

    public List<Class<?>> getClassList(String packageName) {
        return null;
    }

    public List<Class<?>> getClassListByAnnotation(String packageName, Class<? extends Annotation> annotationClass) {
        return null;
    }

    public List<Class<?>> getClassBySuper(String packageName, Class<?> superClass) {
        return null;
    }
}
