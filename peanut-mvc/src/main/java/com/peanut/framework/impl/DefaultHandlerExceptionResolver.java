package com.peanut.framework.impl;

import com.peanut.framework.FrameworkConstant;
import com.peanut.framework.HandlerExceptionResolver;
import com.peanut.framework.fault.AuthcException;
import com.peanut.framework.fault.AuthzException;
import com.peanut.framework.util.WebUtil;
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

    /**
     * 判断异常原因，分两种情况进行处理，跳转到403页面，重定向到首页，跳转到500页面
     *
     * @param request
     * @param response
     */
    @Override
    public void resolveHandlerException(HttpServletRequest request, HttpServletResponse response, Exception e) {

        Throwable cause = e.getCause();
        if (cause instanceof AuthcException) {
            if (WebUtil.isAjax(request)) {
                WebUtil.sendError(HttpServletResponse.SC_FORBIDDEN, "", response);
            } else {
                WebUtil.redirectRequest(FrameworkConstant.HOME_PAGE, request, response);
            }
        } else if (cause instanceof AuthzException) {
            WebUtil.sendError(HttpServletResponse.SC_FORBIDDEN, "", response);
        } else {
            WebUtil.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, cause.getMessage(), response);
        }
    }
}
