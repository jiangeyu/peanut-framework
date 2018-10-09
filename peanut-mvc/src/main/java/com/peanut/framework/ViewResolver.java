package com.peanut.framework;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:22 2018/7/25
 * @desc
 */
public interface ViewResolver {

    /**
     * 视图解析
     *
     * @param request
     * @param response
     * @param actionMethodResult
     */
    void resolveView(HttpServletRequest request, HttpServletResponse response, Object actionMethodResult);
}
