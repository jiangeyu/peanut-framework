package com.peanut.framework.orm;

import com.peanut.framework.ClassHelper;
import com.peanut.framework.orm.annotation.Column;
import com.peanut.framework.orm.annotation.Entity;
import com.peanut.framework.orm.annotation.Table;
import com.peanut.framework.util.ArrayUtil;
import com.peanut.framework.util.StringUtil;
import org.apache.commons.collections.MapUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:30 2018/7/25
 * @desc 初始化entity结构
 */
public class EntityHelper {

    private static final Map<Class<?>, String> entityClassTableNameMap = new HashMap<Class<?>, String>(16);


    private static final Map<Class<?>, Map<String, String>> entityClassFieldMapMap = new HashMap<Class<?>, Map<String, String>>(1024);

    static {
        List<Class<?>> entityClassList = ClassHelper.getClassListByAnnotation(Entity.class);

    }

    private static void initEntityNameMap(Class<?> entityClass) {
        entityClassTableNameMap.put(entityClass, parseTableName(entityClass));
    }

    private static String parseFieldName(Class<?> cls) {
        return cls.isAnnotationPresent(Column.class) ?
                cls.getAnnotation(Column.class).value() : StringUtil.camelhumpToUnderline(cls.getName());
    }

    private static String parseTableName(Class<?> cls) {
        return cls.isAnnotationPresent(Table.class) ? cls.getAnnotation(Table.class).value() : cls.getSimpleName();
    }


    private static void initEntityFieldMap(Class<?> entityClass) {
        Field[] fields = entityClass.getDeclaredFields();
        Map<String, String> fieldMap = new HashMap<>(16);
        if (ArrayUtil.isNotEmpty(fields)) {
            fieldMap = Arrays.stream(fields)
                    .collect(Collectors.toMap(field -> field.getName(), f -> parseFieldName(Column.class), (v1, v2) -> v1));
        }
        entityClassFieldMapMap.put(entityClass, fieldMap);
    }

    public static String getTableName(Class<?> entityClass) {
        Optional<String> optional = Optional.ofNullable(entityClassTableNameMap.get(entityClass));
        return optional.get();
    }

    public static Map<String, String> getFieldMap(Class<?> entityClass) {
        return entityClassFieldMapMap.get(entityClass);
    }

    public static Map<String, String> getColumnMap(Class<?> entityClass) {
        return MapUtils.invertMap(getFieldMap(entityClass));
    }

    public static String getColumnName(Class<?> entityClass, String fieldName) {
        String columnName = getFieldMap(entityClass).get(fieldName);
        return StringUtil.isNotEmpty(columnName) ? columnName : fieldName;
    }
}
