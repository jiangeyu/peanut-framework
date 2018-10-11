package com.peanut.framework;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 上午10:18 2018/9/30
 * @desc  web数据上下文
 */
public class DataContext {

    private static final ThreadLocal<DataContext> dataContextContainer = new ThreadLocal<>();

    private HttpServletRequest request;

    private HttpServletResponse response;

    public static void init(HttpServletRequest request, HttpServletResponse response) {
        DataContext dataContext = new DataContext();
        dataContext.request = request;
        dataContext.response = response;
        dataContextContainer.set(dataContext);
    }

    /**
     * 销毁
     */
    public static void destroy() {
        dataContextContainer.remove();
    }

    /**
     * 获取 DataContext 实例
     */
    public static DataContext getInstance() {
        return dataContextContainer.get();
    }

    /**
     * 获取 Request 对象
     */
    public static HttpServletRequest getRequest() {
        return getInstance().request;
    }

    /**
     * 获取 Response 对象
     */
    public static HttpServletResponse getResponse() {
        return getInstance().response;
    }

    /**
     * 获取 Session 对象
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取 Servlet Context 对象
     */
    public static javax.servlet.ServletContext getServletContext() {
        return getRequest().getServletContext();
    }
}
