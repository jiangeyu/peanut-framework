package com.peanut.framework;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 上午10:21 2018/9/30
 * @desc
 */
public interface HandlerExceptionResolver {

    void resolveHandlerException(HttpServletRequest request, HttpServletResponse response, Exception e);
}
