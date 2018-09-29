package com.peanut.framework.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:07 2018/7/25
 * @desc 数据访问器
 */
public interface DataAccessor {

    <T> T queryEntity(Class<T> entityClass, String sql, Object... params);

    <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params);

    <K, V> Map<K, V> queryEntityMap(Class<V> entityClass, String sql, Object... params);

    Object[] queryArray(String sql, Object... params);

    List<Object[]> queryArrayList(String sql, Object... params);

    Map<String, Object> queryMap(String sql, Object... params);

    List<Map<String, Object>> queryMapList(String sql, Object... params);

    <T> T queryColumn(String sql, Object... params);

    <T> List<T> queryColumnList(String sql, Object... params);

    <T> Map<T, Map<String, Object>> queryColumnMap(String column, String sql, Object... params);

    long queryCount(String sql, Object... params);

    int update(String sql, Object... params);

    Serializable insertReturnPK(String sql, Object... params);


}
