package com.peanut.framework;

import com.peanut.framework.annotation.Aspect;
import com.peanut.framework.annotation.AspectOrder;
import com.peanut.framework.proxy.Proxy;

import java.util.*;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:49 2018/7/13
 * @desc 初始化aop框架
 */
public class AopHelper {

    private static final ClassScanner classScanner = InstanceFactory.getClassScanner();


    private static Map<Class<?>, List<Class<?>>> createProxyMap() throws Exception {
        Map<Class<?>, List<Class<?>>> proxyMap = new LinkedHashMap<Class<?>, List<Class<?>>>();
        addPluginProxy(proxyMap);
        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);
        return proxyMap;
    }

    private static void addPluginProxy(Map<Class<?>, List<Class<?>>> proxyMap) {
//        List<Class<?>> pluginProxyClassList = classScanner.getClassBySuper(FrameworkConstant.PLUGIN_PACKAGE,)

    }

    private static void addAspectProxy(Map<Class<?>, List<Class<?>>> proxyMap) {
        List<Class<?>> aspectProxyClassList = ClassHelper.getClassListBySuper(AspectProxy.class);
        aspectProxyClassList.addAll(classScanner.getClassBySuper(FrameworkConstant.PLUGIN_PACKAGE, AspectProxy.class));

    }

    private static void addTransactionProxy(Map<Class<?>, List<Class<?>>> proxyMap) {

    }

    private static int getOrderValue(Class<?> aspect) {
        return aspect.getAnnotation(AspectOrder.class) != null ? aspect.getAnnotation(AspectOrder.class).value() : 0;
    }

    private static void sortAspectProxyClassList(List<Class<?>> proxyClassList) {
        Collections.sort(proxyClassList, (Class<?> aspect1, Class<?> aspect2) -> {
            if (aspect1.isAnnotationPresent(AspectOrder.class) || aspect2.isAnnotationPresent(AspectOrder.class)) {
                if (aspect1.isAnnotationPresent(AspectOrder.class)) {
                    return getOrderValue(aspect1) - getOrderValue(aspect2);

                } else {
                    return getOrderValue(aspect2) - getOrderValue(aspect1);
                }
            } else {
                return aspect1.hashCode() - aspect2.hashCode();
            }
        });
    }

    private static List<Class<?>> createTargetClassList(Aspect aspect) {
        List<Class<?>> list = new ArrayList<>();
        return list;
    }

    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, List<Class<?>>> proxyMap) {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>(1024);
        return targetMap;
    }

}




