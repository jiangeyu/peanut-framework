package com.peanut.framework.impl;

import com.peanut.framework.FrameworkConstant;
import com.peanut.framework.ViewResolver;
import com.peanut.framework.bean.Result;
import com.peanut.framework.bean.View;
import com.peanut.framework.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:42 2018/7/25
 * @desc 默认视图解析器
 */
public class DefaultViewResolver implements ViewResolver {

    private static final Logger logger = LoggerFactory.getLogger(DefaultViewResolver.class);

    @Override
    public void resolveView(HttpServletRequest request, HttpServletResponse response, Object actionMethodResult) {
        if (actionMethodResult != null) {
            if (actionMethodResult instanceof View) {
                View view = (View) actionMethodResult;
                if (view.isRedirect()) {
                    String path = view.getPath();
                    WebUtil.redirectRequest(path, request, response);
                } else {
                    String path = FrameworkConstant.JSP_PATH + view.getPath();
                    Map<String, Object> data = view.getData();
                    data.entrySet().stream().forEach(key -> request.setAttribute(key.getKey(), key.getValue()));
                    WebUtil.forwardRequest(path, request, response);
                }
            } else {
                Result result = (Result) actionMethodResult;
                //todo 文件上传
            }
        }

    }
}
