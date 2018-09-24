package com.peanut.framework;

import com.peanut.framework.annotation.Impl;
import com.peanut.framework.annotation.Inject;
import com.peanut.framework.fault.InitializationError;
import com.peanut.framework.util.ArrayUtil;
import com.peanut.framework.util.CollectionUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午10:58 2018/7/25
 * @desc 初始化 IOC 容器
 */
public class IocHelper {

    static {
        try {
            Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
            beanMap.entrySet().stream().forEach(entry -> {
                Class<?> beanClass = entry.getKey();
                Object beanInstance = entry.getValue();
                Field[] fields = beanClass.getDeclaredFields();
                if (ArrayUtil.isNotEmpty(fields)) {
                    Arrays.stream(fields)
                            .filter(beanField -> beanField.isAnnotationPresent(Inject.class))
                            .forEach(field -> {
                                Class<?> interfaceClass = field.getType();
                                Class<?> implementClass = findImplementClass(interfaceClass);
                                if (implementClass != null) {
                                    Object implementInstance = beanMap.get(implementClass);
                                    if (implementInstance != null) {
                                        field.setAccessible(true);
                                        try {
                                            field.set(beanInstance, implementInstance);
                                        } catch (IllegalAccessException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } else {
                                    throw new InitializationError("依赖注入失败!类名" + beanClass.getSimpleName() + "字段名" + interfaceClass.getSimpleName());
                                }
                            });
                }
            });
        } catch (Exception e) {
            throw new InitializationError("初始化 IocHelper 出错！", e);
        }

    }


    public static Class<?> findImplementClass(Class<?> interfaceClass) {
        Class<?> implementClass = interfaceClass;

        if (interfaceClass.isAnnotationPresent(Impl.class)) {
            implementClass = interfaceClass.getAnnotation(Impl.class).value();
        } else {
            List<Class<?>> implementClassList = ClassHelper.getClassListBySuper(interfaceClass);
            if (CollectionUtil.isNotEmpty(implementClassList)) {
                implementClass = implementClassList.get(0);
            }
        }
        return implementClass;
    }
}
