package com.peanut.framework.impl.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:13 2018/7/24
 * @desc 用于获取类的模板类
 */
public abstract class ClassTemplate {

    private static final Logger logger = LoggerFactory.getLogger(ClassTemplate.class);

    protected final String packageName;

    public ClassTemplate(String packageName) {
        this.packageName = packageName;
    }

    public final List<Class<?>> getClassList() {
        List<Class<?>> classList = new ArrayList<Class<?>>();
        return classList;
    }


    public abstract boolean checkAddClass(Class<?> cls);
}
