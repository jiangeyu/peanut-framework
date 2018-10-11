package com.peanut.framework;

import com.peanut.framework.dao.DataAccessor;
import com.peanut.framework.dao.impl.DefaultDataAccessor;
import com.peanut.framework.ds.DataSourceFactory;
import com.peanut.framework.ds.impl.DefaultDataSourceFactory;
import com.peanut.framework.impl.*;
import com.peanut.framework.util.ObjectUtil;
import com.peanut.framework.util.StringUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 上午1:31 2018/9/24
 * @desc 实例工厂
 */
public class InstanceFactory {

    private static final Map<String, Object> cache = new ConcurrentHashMap<>(64);

    /**
     * ClassScanner
     */
    private static final String CLASS_SCANNER = "com.peanut.framework.impl.DefaultClassScanner";

    /**
     * DataSourceFactory
     */
    private static final String DS_FACTORY = "com.peanut.framework.custom.ds_factory";

    /**
     * DataAccessor
     */
    private static final String DATA_ACCESSOR = "com.peanut.framework.custom.data_accessor";

    /**
     * HandlerMapping
     */
    private static final String HANDLER_MAPPING = "com.peanut.framework.custom.handler_mapping";

    /**
     * HandlerInvoker
     */
    private static final String HANDLER_INVOKER = "com.peanut.framework.custom.handler_invoker";

    /**
     * HandlerExceptionResolver
     */
    private static final String HANDLER_EXCEPTION_RESOLVER = "com.peanut.framework.custom.handler_exception_resolver";

    /**
     * ViewResolver
     */
    private static final String VIEW_RESOLVER = "com.peanut.framework.custom.view_resolver";

    /**
     * 获取 ClassScanner
     */
    public static ClassScanner getClassScanner() {
        return getInstance(CLASS_SCANNER, DefaultClassScanner.class);
    }

    /**
     * 获取 DataSourceFactory
     */
    public static DataSourceFactory getDataSourceFactory() {
        return getInstance(DS_FACTORY, DefaultDataSourceFactory.class);
    }

    /**
     * 获取 DataAccessor
     */
    public static DataAccessor getDataAccessor() {
        return getInstance(DATA_ACCESSOR, DefaultDataAccessor.class);
    }

    /**
     * 获取 HandlerMapping
     */
    public static HandlerMapping getHandlerMapping() {
        return getInstance(HANDLER_MAPPING, DefaultHandlerMapping.class);
    }

    /**
     * 获取 HandlerInvoker
     */
    public static HandlerInvoker getHandlerInvoker() {
        return getInstance(HANDLER_INVOKER, DefaultHandlerInvoker.class);
    }

    /**
     * 获取 HandlerExceptionResolver
     */
    public static HandlerExceptionResolver getHandlerExceptionResolver() {
        return getInstance(HANDLER_EXCEPTION_RESOLVER, DefaultHandlerExceptionResolver.class);
    }

    /**
     * 获取 ViewResolver
     */
    public static ViewResolver getViewResolver() {
        return getInstance(VIEW_RESOLVER, DefaultViewResolver.class);
    }

    public static <T> T getInstance(String cacheKey, Class<T> defaultImplClass) {
        if (cacheKey.contains(cacheKey)) {
            return (T) cache.get(cacheKey);
        }
        String implClassName = ConfigHelper.getString(cacheKey);
        if (StringUtil.isNotEmpty(implClassName)) {
            implClassName = defaultImplClass.getName();
        }
        T instance = ObjectUtil.newInstance(implClassName);
        if (instance != null) {
            cache.putIfAbsent(cacheKey, instance);
        }
        return instance;
    }
}
