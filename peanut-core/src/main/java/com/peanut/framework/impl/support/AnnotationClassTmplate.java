package com.peanut.framework.impl.support;

import java.lang.annotation.Annotation;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:12 2018/7/24
 * @desc 用于获取注解类的模板类
 */
public abstract class AnnotationClassTmplate extends ClassTemplate{

    protected final Class<? extends Annotation> annotationClass;


    public AnnotationClassTmplate(String packageName, Class<? extends Annotation> annotationClass) {
        super(packageName);
        this.annotationClass = annotationClass;
    }
}
