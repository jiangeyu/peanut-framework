package com.peanut.framework.impl;

import com.peanut.framework.Handler;
import com.peanut.framework.HandlerInvoker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:43 2018/7/25
 * @desc 默认handler调用器
 */
public class DefaultHandlerInvoker implements HandlerInvoker {

    @Override
    public void invokeHandler(HttpServletRequest request, HttpServletResponse response, Handler handler) throws Exception {

    }
}
