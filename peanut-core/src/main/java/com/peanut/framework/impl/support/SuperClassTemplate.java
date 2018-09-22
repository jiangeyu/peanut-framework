package com.peanut.framework.impl.support;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:13 2018/7/24
 * @desc 获取子类的模板类
 */
public abstract class SuperClassTemplate extends ClassTemplate {

    protected final Class<?> superClass;

    public SuperClassTemplate(String packageName, Class<?> superClass) {
        super(packageName);
        this.superClass = superClass;
    }
}
