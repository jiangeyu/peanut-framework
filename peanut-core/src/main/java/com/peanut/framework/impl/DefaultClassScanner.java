package com.peanut.framework.impl;

import com.peanut.framework.ClassScanner;
import com.peanut.framework.impl.support.AnnotationClassTemplate;
import com.peanut.framework.impl.support.ClassTemplate;
import com.peanut.framework.impl.support.SuperClassTemplate;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:27 2018/7/23
 * @desc 默认类扫描器
 */
public class DefaultClassScanner implements ClassScanner {

    @Override
    public List<Class<?>> getClassList(String packageName) {
        return new ClassTemplate(packageName) {
            @Override
            public boolean checkAddClass(Class<?> cls) {
                String className = cls.getName();
                String pkgName = className.substring(0, className.lastIndexOf("."));

                return pkgName.startsWith(packageName);
            }
        }.getClassList();
    }

    @Override
    public List<Class<?>> getClassListByAnnotation(String packageName, Class<? extends Annotation> annotationClass) {
        return new AnnotationClassTemplate(packageName, annotationClass) {

            @Override
            public boolean checkAddClass(Class<?> cls) {
                return cls.isAnnotationPresent(annotationClass);
            }
        }.getClassList();
    }

    @Override
    public List<Class<?>> getClassBySuper(String packageName, Class<?> superClass) {
        return new SuperClassTemplate(packageName, superClass) {
            @Override
            public boolean checkAddClass(Class<?> cls) {
                return superClass.isAssignableFrom(cls) && !superClass.equals(superClass);
            }
        }.getClassList();
    }
}
