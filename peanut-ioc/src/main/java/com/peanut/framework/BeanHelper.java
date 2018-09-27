package com.peanut.framework;

import com.peanut.framework.annotation.Aspect;
import com.peanut.framework.annotation.Bean;
import com.peanut.framework.tx.annotation.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午10:59 2018/7/25
 * @desc 初始化相关 Bean 类
 */
public class BeanHelper {

    private static final Map<Class<?>, Object> beanMap = new HashMap<Class<?>, Object>(16);


    static {
        List<Class<?>> classList = ClassHelper.getClassList();
        classList.stream()
                .filter(cls -> cls.isAnnotationPresent(Bean.class)
                            || cls.isAnnotationPresent(Service.class)
                            || cls.isAnnotationPresent(Aspect.class))
                .forEach(clazz -> {
            try {
                beanMap.putIfAbsent(clazz, clazz.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public static Map<Class<?>, Object> getBeanMap() {
        return beanMap;
    }

    public static <T> T getBean(Class<T> tClass) {
        if (!beanMap.containsKey(tClass)) {
            throw new RuntimeException("无法根据类名获取实例! " + tClass);
        }
        return (T) beanMap.get(tClass);
    }

    public static void setBean(Class<?> cls, Object o) {
        beanMap.putIfAbsent(cls, o);
    }
}
