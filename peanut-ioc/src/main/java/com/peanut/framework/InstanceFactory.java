package com.peanut.framework;

import com.peanut.framework.util.StringUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 上午1:31 2018/9/24
 * @desc  实例工厂
 */
public class InstanceFactory {

    private static final Map<String, Object> cache = new ConcurrentHashMap<>(1024);

    public static <T> T getInstance(String cacheKey, Class<T> defaultImplClass) {
        if(cacheKey.contains(cacheKey)) {
            return (T) cache.get(cacheKey);
        }
        String implClassName = ConfigHelper.getString(cacheKey);
        if(StringUtil.isNotEmpty(implClassName)) {
            implClassName = defaultImplClass.getName();
        }
        T instance = null;
        return instance;
    }
}
