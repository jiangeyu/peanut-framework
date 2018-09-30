package com.peanut.framework;

import com.peanut.framework.annotation.Action;
import com.peanut.framework.annotation.Request;
import com.peanut.framework.util.ArrayUtil;
import com.peanut.framework.util.CollectionUtil;
import com.peanut.framework.util.StringUtil;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 上午10:17 2018/9/30
 * @desc 初始化Action配置
 */
public class ActionHelper {

    private static final Map<RequestMessage, Handler> actionMap = new LinkedHashMap<RequestMessage, Handler>();

    static {
        List<Class<?>> actionClassList = ClassHelper.getClassListByAnnotation(Action.class);
        if (CollectionUtil.isNotEmpty(actionClassList)) {
            Map<RequestMessage, Handler> commonActionMap = new HashMap<>();
            Map<RequestMessage, Handler> regexpActionMap = new HashMap<>();
            actionClassList.forEach(cls -> {
                Method[] methods = cls.getMethods();
                if (ArrayUtil.isNotEmpty(methods)) {
                    Arrays.asList(methods).forEach(method -> handlerActionMethod(cls, method, commonActionMap, regexpActionMap));
                }
            });
            actionMap.putAll(commonActionMap);
            actionMap.putAll(regexpActionMap);
        }

    }

    private static void handlerActionMethod(Class<?> actionClass, Method actionMethod, Map<RequestMessage, Handler> commonActionMap, Map<RequestMessage, Handler> regexpActionMap) {
        if (actionMethod.isAnnotationPresent(Request.Get.class)) {
            String requestPath = actionMethod.getAnnotation(Request.Get.class).value();
            putActionMap("GET", requestPath, actionClass, actionMethod, commonActionMap, regexpActionMap);
        } else if (actionMethod.isAnnotationPresent(Request.Post.class)) {
            String requestPath = actionMethod.getAnnotation(Request.Post.class).value();
            putActionMap("POST", requestPath, actionClass, actionMethod, commonActionMap, regexpActionMap);
        } else if (actionMethod.isAnnotationPresent(Request.Put.class)) {
            String requestPath = actionMethod.getAnnotation(Request.Put.class).value();
            putActionMap("PUT", requestPath, actionClass, actionMethod, commonActionMap, regexpActionMap);
        } else if (actionMethod.isAnnotationPresent(Request.Delete.class)) {
            String requestPath = actionMethod.getAnnotation(Request.Delete.class).value();
            putActionMap("DELETE", requestPath, actionClass, actionMethod, commonActionMap, regexpActionMap);
        }

    }


    private static void putActionMap(String requestMethod, String requestPath, Class<?> actionClass, Method actionMethod, Map<RequestMessage, Handler> commonActionMap, Map<RequestMessage, Handler> regexpActionMap) {
        if (requestPath.matches(".+\\{\\w+\\}.*")) {
            requestPath = StringUtil.replaceAll(requestPath, "\\{\\w+\\}", "{\\\\w+}");
            actionMap.put(new RequestMessage(requestMethod, requestPath), new Handler(actionClass, actionMethod));
        } else {
            commonActionMap.put(new RequestMessage(requestMethod, requestPath), new Handler(actionClass, actionMethod));

        }
    }


    public static Map<RequestMessage, Handler> getActionMap() {
        return actionMap;
    }

}
