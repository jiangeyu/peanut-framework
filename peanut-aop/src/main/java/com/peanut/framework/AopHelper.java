package com.peanut.framework;

import com.peanut.framework.annotation.Aspect;
import com.peanut.framework.annotation.AspectOrder;
import com.peanut.framework.fault.InitializationError;
import com.peanut.framework.proxy.Proxy;
import com.peanut.framework.proxy.ProxyManager;
import com.peanut.framework.tx.TransactionProxy;
import com.peanut.framework.annotation.Service;
import com.peanut.framework.util.ClassUtil;
import com.peanut.framework.util.StringUtil;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:49 2018/7/13
 * @desc 初始化aop框架
 */
public class AopHelper {

    private static final ClassScanner classScanner = InstanceFactory.getClassScanner();

    static {
        try {
            Map<Class<?>, List<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            targetMap.entrySet().stream()
                    .forEach(entry -> {
                        Class<?> targetClass = entry.getKey();
                        List<Proxy> proxyList = entry.getValue();
                        Object proxyInstance = ProxyManager.createProxy(targetClass, proxyList);
                        BeanHelper.setBean(targetClass, proxyInstance);
                    });
        } catch (Exception e) {
            throw new InitializationError("初始化AOPHelper出错", e);
        }
    }


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
        sortAspectProxyClassList(aspectProxyClassList);

        aspectProxyClassList.stream()
                .filter(aspectProxyClass -> aspectProxyClass.isAnnotationPresent(Aspect.class))
                .forEach(proxyClass -> {
                    Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                    List<Class<?>> targetClassList = createTargetClassList(aspect);
                    proxyMap.putIfAbsent(proxyClass, targetClassList);
                });

    }

    private static void addTransactionProxy(Map<Class<?>, List<Class<?>>> proxyMap) {
        List<Class<?>> serviceClassList = ClassHelper.getClassListByAnnotation(Service.class);
        proxyMap.put(TransactionProxy.class, serviceClassList);

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
        List<Class<?>> targetClassList = new ArrayList<>();
        String pkg = aspect.pkg();
        String cls = aspect.cls();
        Class<? extends Annotation> annotation = aspect.annotion();
        if (StringUtil.isNotEmpty(pkg)) {
            if (StringUtil.isNotEmpty(cls)) {
                targetClassList.add(ClassUtil.loadClass(pkg + "." + cls, false));
            } else {
                if (annotation != null && !annotation.equals(Aspect.class)) {
                    targetClassList.addAll(classScanner.getClassListByAnnotation(pkg, annotation));
                } else {
                    targetClassList.addAll(classScanner.getClassList(pkg));
                }
            }
        } else {
            if (annotation != null && !annotation.equals(Aspect.class)) {
                targetClassList.addAll(ClassHelper.getClassListByAnnotation(annotation));
            }
        }
        return targetClassList;
    }

    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, List<Class<?>>> proxyMap) {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>(1024);
        proxyMap.entrySet().stream().forEach(entry -> {
            Class<?> proxyClass = entry.getKey();
            List<Class<?>> targetClassList = entry.getValue();
            targetClassList.stream().forEach(targetClass -> {
                try {
                    Proxy proxy = (Proxy) proxyClass.newInstance();
                    if (targetMap.containsKey(targetClass)) {
                        targetMap.get(targetClass).add(proxy);
                    } else {
                        List<Proxy> baseAspectList = new ArrayList<>();
                        baseAspectList.add(proxy);
                        targetMap.put(targetClass, baseAspectList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
        return targetMap;
    }

}




