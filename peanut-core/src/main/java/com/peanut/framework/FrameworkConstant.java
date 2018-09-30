package com.peanut.framework;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午10:20 2018/9/23
 * @desc
 */
public interface FrameworkConstant {

    String UTF_8 = "UTF_8";

    String CONFIG_PROPS = "peanut.properties";
    String SQL_PROPS = "peanut-sql.properties";

    String PLUGIN_PACKAGE = "com.peanut.plugin";


    String JSP_PATH = ConfigHelper.getString("smart.framework.app.jsp_path", "/WEB-INF/jsp/");
    String WWW_PATH = ConfigHelper.getString("smart.framework.app.www_path", "/www/");
    String HOME_PAGE = ConfigHelper.getString("smart.framework.app.home_page", "/index.html");
    int UPLOAD_LIMIT = ConfigHelper.getInt("smart.framework.app.upload_limit", 10);

    String PK_NAME = "id";

}
