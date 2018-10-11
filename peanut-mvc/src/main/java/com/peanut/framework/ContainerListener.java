package com.peanut.framework;

import com.peanut.framework.util.StringUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 上午10:18 2018/9/30
 * @desc 容器监听器,类似SpringMVC 的ContextListener
 */
@WebListener
public class ContainerListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sc) {
        ServletContext servletContext = sc.getServletContext();
        HelperLoader.init();
        addServletMapping(servletContext);
        registryWebPlugin(servletContext);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        destroyPlugin();
    }

    private void addServletMapping(ServletContext context) {
        registryDefaultServlet(context);
        registryJspServlet(context);
    }

    private void registryDefaultServlet(ServletContext context) {
        ServletRegistration defaultServlet = context.getServletRegistration("default");
        defaultServlet.addMapping("/index.html");
        defaultServlet.addMapping("/favicon.ico");
        String wwwPath = FrameworkConstant.WWW_PATH;
        if (StringUtil.isNotEmpty(wwwPath)) {
            defaultServlet.addMapping(wwwPath + "*");
        }

    }

    private void registryJspServlet(ServletContext context) {
        ServletRegistration defaultServlet = context.getServletRegistration("jsp");
        defaultServlet.addMapping("/index.jsp");
        String wwwPath = FrameworkConstant.JSP_PATH;
        if (StringUtil.isNotEmpty(wwwPath)) {
            defaultServlet.addMapping(wwwPath + "*");
        }
    }

    private void registryWebPlugin(ServletContext context) {


    }

    private void destroyPlugin() {

    }
}
