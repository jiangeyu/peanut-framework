package com.peanut.framework.impl;

import com.peanut.framework.Handler;
import com.peanut.framework.HandlerMapping;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:43 2018/7/25
 * @desc 默认处理器映射
 */
public class DefaultHandlerMapping implements HandlerMapping {

    @Override
    public Handler getHandler(String currentRequestMethod, String currentRequestPath) {
        Handler handler = null;
//        Map<Request, Handler> actionMap = ActionHelper
        return handler;
    }
}