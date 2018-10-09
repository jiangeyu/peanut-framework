package com.peanut.framework;

import com.peanut.framework.dao.DatabaseHelper;
import com.peanut.framework.orm.EntityHelper;
import com.peanut.framework.plugin.PluginHelper;
import com.peanut.framework.util.ClassUtil;

import java.util.Arrays;
import java.util.List;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午4:10 2018/9/29
 * @desc
 */
public final class HelperLoader {

    /**
     * 定义需要加载的 Helper 类
     */
    public static void init() {

        List<Class> classList = Arrays.asList(new Class[]{DatabaseHelper.class, EntityHelper.class, ActionHelper.class,
                BeanHelper.class, AopHelper.class, IocHelper.class, PluginHelper.class});

        classList.stream().forEach(cls -> ClassUtil.loadClass(cls.getName()));
    }
}
