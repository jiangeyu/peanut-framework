package com.peanut.framework;

import com.peanut.framework.dao.DatabaseHelper;
import com.peanut.framework.orm.EntityHelper;
import com.peanut.framework.util.ClassUtil;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午4:10 2018/9/29
 * @desc
 */
public final class HelperLoader {
    public static void init() {
        // 定义需要加载的 Helper 类
        Class<?>[] classList = {
                DatabaseHelper.class,
                EntityHelper.class,
//                ActionHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
//                PluginHelper.class,
        };
        // 按照顺序加载类
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName());
        }
    }
}
