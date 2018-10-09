package com.peanut.framework.impl;

import com.peanut.framework.*;
import com.peanut.framework.bean.Params;
import com.peanut.framework.util.CastUtil;
import com.peanut.framework.util.ClassUtil;
import com.peanut.framework.util.WebUtil;
import org.apache.commons.collections.MapUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:43 2018/7/25
 * @desc 默认handler调用器
 */
public class DefaultHandlerInvoker implements HandlerInvoker {

    private ViewResolver viewResolver = InstanceFactory.getViewResolver();

    /**
     *  获取 Action 相关信息
     *  从 BeanHelper 中创建 Action 实例
     *  创建 Action 方法的参数列表
     *  检查参数列表是否合法
     *  调用 Action 方法
     *  解析视图
     *
     * @param request
     * @param response
     * @param handler
     * @throws Exception
     */

    @Override
    public void invokeHandler(HttpServletRequest request, HttpServletResponse response, Handler handler) throws Exception {

        Class<?> actionClass = handler.getActionClass();
        Method actionMethod = handler.getActionMethod();

        Object actionInstance = BeanHelper.getBean(actionClass);

        List<Object> actionMethodParamList = createActionMethodParamList(request, handler);

        checkParamList(actionMethod, actionMethodParamList);

        Object actionMethodResult = invokeActionMethod(actionMethod, actionInstance, actionMethodParamList);

        viewResolver.resolveView(request, response, actionMethodResult);
    }

    /**
     *  获取 Action 方法参数类型
     *  添加路径参数列表（请求路径中的带占位符参数）
     *
     *  分两种情况进行处理
     *
     *  添加普通请求参数列表（包括 Query String 与 Form Data）
     *
     *  返回参数列表
     *
     * @param request
     * @param handler
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public List<Object> createActionMethodParamList(HttpServletRequest request, Handler handler) throws InvocationTargetException, IllegalAccessException {
        List<Object> paramList = new ArrayList<>(16);

        Class<?>[] actionParamTypes = handler.getActionMethod().getParameterTypes();

        paramList.addAll(createPathParamList(handler.getRequestPathMatcher(), actionParamTypes));

        Map<String, Object> requestParamMap = WebUtil.getRequestParamMap(request);
        if (MapUtils.isNotEmpty(requestParamMap)) {
            paramList.add(new Params(requestParamMap));
        }

        return paramList;
    }

    private List<Object> createPathParamList(Matcher requestPathMatcher, Class<?>[] actionParamTypes) {
        List<Object> paramList = new ArrayList<>(16);
        int length = requestPathMatcher.groupCount();
        for (int i = 0; i <= length; i++) {
            String param = requestPathMatcher.group(i);
            Class<?> paramType = actionParamTypes[i];
            if (ClassUtil.isInt(paramType)) {
                paramList.add(CastUtil.castInt(param));
            } else if (ClassUtil.isLong(paramType)) {
                paramList.add(CastUtil.castLong(param));
            } else if (ClassUtil.isDouble(paramType)) {
                paramList.add(CastUtil.castDouble(param));
            } else if (ClassUtil.isString(paramType)) {
                paramList.add(param);
            }
        }
        return paramList;
    }

    private Object invokeActionMethod(Method actionMethod, Object actionInstance, List<Object> actionMethodParamList) throws InvocationTargetException, IllegalAccessException {
        actionMethod.setAccessible(true);
        return actionMethod.invoke(actionInstance, actionMethodParamList.toArray());
    }

    private void checkParamList(Method actionMethod, List<Object> actionMethodParamList) {
        Class<?>[] actionMethodParameterTypes = actionMethod.getParameterTypes();
        if (actionMethodParameterTypes.length != actionMethodParamList.size()) {
            String message = String.format("因为参数个数不匹配，所以无法调用 Action 方法！原始参数个数：%d，实际参数个数：%d", actionMethodParameterTypes.length, actionMethodParamList.size());
            throw new RuntimeException(message);
        }
    }
}
