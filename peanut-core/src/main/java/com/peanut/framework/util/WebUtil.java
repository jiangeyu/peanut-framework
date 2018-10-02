package com.peanut.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午5:37 2018/9/30
 * @desc web操作工具类
 */
public class WebUtil {

    private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);

    public static void writeJSON(HttpServletResponse response, Object data) {

    }

    public static void writeHTML(HttpServletResponse response,Object data) {

    }

    public static Map<String, Object> getRequestParamMap(HttpServletRequest request) {
        Map<String, Object> map = new LinkedHashMap<>();

        return map;
    }


    public static boolean checkParamName(String paramName) {
        return !paramName.equals("_");
    }

    public static void forwardRequest(String path, HttpServletRequest request, HttpServletResponse response) {

    }

    public static String getRequestPath(HttpServletRequest request) {
        String path = request.getServletPath();
        String pathInfo = StringUtil.defaultIfEmpty(request.getPathInfo(), "");
        return path + pathInfo;
    }
}
