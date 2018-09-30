package com.peanut.framework.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午5:37 2018/9/30
 * @desc
 */
public class WebUtil {

    public static String getRequestPath(HttpServletRequest request) {
        String path = request.getServletPath();
        String pathInfo = StringUtil.defaultIfEmpty(request.getPathInfo(), "");
        return path + pathInfo;
    }
}
