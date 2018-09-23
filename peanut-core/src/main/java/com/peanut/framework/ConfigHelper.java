package com.peanut.framework;

import com.peanut.framework.util.PropsUtil;

import java.util.Map;
import java.util.Properties;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:53 2018/7/13
 * @desc 获取属性文件中的属性值
 */
public class ConfigHelper {

    private static final Properties configProps = PropsUtil.loadProps(FrameworkConstant.CONFIG_PROPS);

    /**
     * 获取 String 类型的属性值
     */
    public static String getString(String key) {
        return PropsUtil.getString(configProps, key);
    }

    /**
     * 获取 String 类型的属性值（可指定默认值）
     */
    public static String getString(String key, String defaultValue) {
        return PropsUtil.getString(configProps, key, defaultValue);
    }

    /**
     * 获取 int 类型的属性值
     */
    public static int getInt(String key) {
        return PropsUtil.getNumber(configProps, key);
    }

    /**
     * 获取 int 类型的属性值（可指定默认值）
     */
    public static int getInt(String key, int defaultValue) {
        return PropsUtil.getNumber(configProps, key, defaultValue);
    }

    /**
     * 获取 boolean 类型的属性值
     */
    public static boolean getBoolean(String key) {
        return PropsUtil.getBoolean(configProps, key);
    }

    /**
     * 获取 int 类型的属性值（可指定默认值）
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        return PropsUtil.getBoolean(configProps, key, defaultValue);
    }

    /**
     * 获取指定前缀的相关属性
     *
     */
    public static Map<String, Object> getMap(String prefix) {
        return PropsUtil.getMap(configProps, prefix);
    }
}
