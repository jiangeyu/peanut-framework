package com.peanut.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:59 2018/9/21
 * @desc 类操作工具类
 */
public class ClassUtil {

    private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取类加载器
     *
     * @return
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 获取类路径
     *
     * @return
     */
    public static String getClassPath() {
        String classPath = "";
        URL resource = getClassLoader().getResource("");
        if (resource != null) {
            classPath = resource.getPath();
        }
        return classPath;
    }

    /**
     * 加载类(将自动初始化)
     *
     * @param className
     * @return
     */
    public static Class<?> loadClass(String className) {
        return loadClass(className, true);
    }

    /**
     * 加载类
     *
     * @param className
     * @param isInitialized
     * @return
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> cls;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());

        } catch (ClassNotFoundException e) {
            logger.error("类加载出错！", e);
            throw new RuntimeException(e);
        }
        return cls;
    }

    /**
     * 是否为int类型(包括Integer类型)
     *
     * @param type
     * @return
     */
    public boolean isInt(Class<?> type) {
        return type.equals(int.class) || type.equals(Integer.class);
    }

    /**
     * 是否为long类型(包括Long类型)
     *
     * @param type
     * @return
     */
    public boolean isLong(Class<?> type) {
        return type.equals(long.class) || type.equals(Long.class);
    }

    /**
     * 是否为double类型(包括Double类型)
     *
     * @param type
     * @return
     */
    public boolean isDouble(Class<?> type) {
        return type.equals(double.class) || type.equals(Double.class);
    }

    /**
     * 是否为String类型
     *
     * @param type
     * @return
     */
    public boolean isString(Class<?> type) {
        return type.equals(String.class);
    }


}
