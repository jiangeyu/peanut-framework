package com.peanut.framework.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午8:17 2018/9/24
 * @desc 对象操作工具类
 */
public class ObjectUtil {

    private static final Logger logger = LoggerFactory.getLogger(ObjectUtil.class);

    public static void setField(Object obj, String fieldName, Object fieldValue) {
        try {
            if (PropertyUtils.isWriteable(obj, fieldName)) {
                PropertyUtils.setProperty(obj, fieldName, fieldValue);
            }
        } catch (Exception e) {
            logger.error("设置成员变量出错！", e);
            throw new RuntimeException();
        }
    }

    public static Object getFieldValue(Object obj, String fieldName) {
        Object propertyValue = null;
        try {
            if (PropertyUtils.isReadable(obj, fieldName)) {
                propertyValue = PropertyUtils.getProperty(obj, fieldName);
            }
        } catch (Exception e) {
            logger.error("获取成员变量出错!", e);
            throw new RuntimeException();
        }
        return propertyValue;
    }

    public static void copyFields(Object source, Object target) {
        try {
            for (Field field : source.getClass().getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    field.setAccessible(true);
                    field.set(target, field.get(source));
                }
            }
        } catch (Exception e) {
            logger.error("复制成员变量出错!", e);
            throw new RuntimeException();
        }
    }

    public static <T> T newInstance(String className) {
        T instance;
        try {
            Class<?> commandClass = ClassUtil.loadClass(className);
            instance = (T) commandClass.newInstance();
        } catch (Exception e) {
            logger.error("创建实例出错!", e);
            throw new RuntimeException();
        }
      return instance;
    }

    public static Map<String, Object> getFieldMap(Object obj) {
        return getFieldMap(obj, true);
    }

    public static Map<String, Object> getFieldMap(Object obj,boolean isStaticIgnore) {
        Field[] fields = obj.getClass().getDeclaredFields();
        Map<String, Object> fieldMap = fieldMap = Arrays.asList(fields).stream()
                .filter(field -> isStaticIgnore&&Modifier.isStatic(field.getModifiers()))
                .collect(Collectors.toMap(f ->f.getName(), v -> ObjectUtil.getFieldValue(obj, v.getName())));

        return fieldMap;
    }

}
