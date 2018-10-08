package com.peanut.framework.impl;

import com.peanut.framework.HandlerExceptionResolver;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:44 2018/7/25
 * @desc 默认handler异常解析器
 */
@Slf4j
public class DefaultHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public void resolveHandlerException(HttpServletRequest request, HttpServletResponse response) {

    }
}
