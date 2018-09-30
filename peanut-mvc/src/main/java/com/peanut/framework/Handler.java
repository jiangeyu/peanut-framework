package com.peanut.framework;

import lombok.Builder;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.regex.Matcher;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 上午10:03 2018/9/30
 * @desc 封装Action 方法相关信息
 */
@Builder
@Data
public class Handler {

    private Class<?> actionClass;

    private Method actionMethod;

    private Matcher requestPathMatcher;

    public Handler(Class<?> actionClass, Method actionMethod) {
        this.actionClass = actionClass;
        this.actionMethod = actionMethod;
    }
}
