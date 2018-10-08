package com.peanut.framework.impl;

import com.peanut.framework.ActionHelper;
import com.peanut.framework.Handler;
import com.peanut.framework.HandlerMapping;
import com.peanut.framework.RequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:43 2018/7/25
 * @desc 默认处理器映射
 */
public class DefaultHandlerMapping implements HandlerMapping {

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 定义一个handler
     * 获取并遍历Action映射
     * 从requestMessage 中获取 request相关信息
     *
     * @param currentRequestMethod
     * @param currentRequestPath
     * @return
     */

    @Override
    public Handler getHandler(String currentRequestMethod, String currentRequestPath) {
        Map<RequestMessage, Handler> actionMap = ActionHelper.getActionMap();
        Optional<Handler> optional = actionMap.entrySet().stream().map(entry -> {
            RequestMessage message = entry.getKey();
            String requestMethod = message.getRequestMethod();
            String requestPath = message.getRequestPath();
            Matcher matcher = Pattern.compile(requestPath).matcher(currentRequestMethod);
            boolean result = matcher.matches() && requestMethod.equalsIgnoreCase(currentRequestMethod);
            Handler handler = entry.getValue();
            if (result) {
                handler = entry.getValue();
                handler.setRequestPathMatcher(matcher);
                return handler;
            }
            return handler;
        }).findAny();
        logger.info("get handler success");
        return optional.get();
    }
}
