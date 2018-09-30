package com.peanut.framework;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午5:24 2018/9/30
 * @desc
 */
public interface HandlerInvoker {

    void invokeHandler(HttpServletRequest request, HttpServletResponse response, Handler handler) throws Exception;
}
