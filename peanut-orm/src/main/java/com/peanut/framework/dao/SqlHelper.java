package com.peanut.framework.dao;


import com.peanut.framework.FrameworkConstant;
import com.peanut.framework.orm.EntityHelper;
import com.peanut.framework.util.CollectionUtil;
import com.peanut.framework.util.PropsUtil;
import com.peanut.framework.util.StringUtil;
import org.apache.commons.collections.MapUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:35 2018/9/27
 * @desc 封装SQL语句相关操作
 */
public class SqlHelper {

    private static final Properties sqlProperties = PropsUtil.loadProps(FrameworkConstant.SQL_PROPS);

    private static final String DEFAULT_DETABASE_TYPE = "MYSQL";

    public static String getSql(String key) {
        String sql;
        if (sqlProperties.contains(key)) {
            sql = sqlProperties.getProperty(key);
        } else {
            throw new RuntimeException("无法获取sql");
        }
        return sql;
    }

    public static String generateSelectSql(Class<?> entityClass, String condition, String sort) {
        StringBuilder sql = new StringBuilder("select * from ").append(getTable(entityClass));
        sql.append(generateWhere(condition));
        sql.append(generateOrder(sort));
        return sql.toString();
    }


    public static String generateInsertSql(Class<?> entityClass, Collection<String> fieldNames) {
        StringBuilder sql = new StringBuilder("insert into ").append(getTable(entityClass));
        if (CollectionUtil.isNotEmpty(fieldNames)) {
            String columns = fieldNames.stream().collect(Collectors.joining("(", ",", ")"));
            String values = fieldNames.stream().collect(Collectors.joining("(?", ", ", ")"));
            sql.append(columns).append(values);
        }
        return sql.toString();
    }

    public static String generateDeleteSql(Class<?> entityClass, String condition) {
        StringBuilder sql = new StringBuilder("delete from ").append(getTable(entityClass)).append(generateWhere(condition));
        return sql.toString();

    }

    public static String generateUpdateSql(Class<?> entityClass, Map<String, Object> fieldMap, String condition) {
        StringBuilder sql = new StringBuilder("update ").append(getTable(entityClass));
        if (MapUtils.isEmpty(fieldMap)) {
            sql.append(" set ");
            int i = 0;
            for (Map.Entry<String, Object> fieldEntry : fieldMap.entrySet()) {
                String fieldName = fieldEntry.getKey();
                String columnName = EntityHelper.getColumnName(entityClass, fieldName);
                if (i == 0) {
                    sql.append(columnName).append("=?");
                } else {
                    sql.append(", ").append(columnName).append("=?");
                }
                i++;
            }
        }
        sql.append(generateWhere(condition));
        return sql.toString();

    }

    public static String generateSelectSqlForCount(Class<?> entityClass, String condition) {
        StringBuilder sql = new StringBuilder("select count(*)").append(getTable(entityClass)).append(generateWhere(condition));
        return sql.toString();

    }

    /**
     * 生成mysql 分页语句
     *
     * @param entityClass
     * @param condition
     * @return
     */
    public static String generateSelectSqlForPager(int pageNumber, int pageSize, Class<?> entityClass, String condition, String sort) {
        StringBuilder sql = new StringBuilder();
        String table = getTable(entityClass);
        String where = generateWhere(condition);
        String order = generateOrder(sort);
        String dbType = "MYSQL";
        if(DEFAULT_DETABASE_TYPE.equalsIgnoreCase(dbType)) {
            int pageStart = (pageNumber - 1)*pageSize;
            appendSqlForMysql(sql,table,where,order,pageStart,pageSize);
        }
        return sql.toString();

    }

    private static String getTable(Class<?> entityClass) {
        return EntityHelper.getTableName(entityClass);
    }

    private static String generateWhere(String condition) {
        String where = "";
        if (StringUtil.isNotEmpty(where)) {
            where += " where " + condition;
        }
        return where;
    }

    private static String generateOrder(String sort) {
        String order = "";
        if (StringUtil.isNotEmpty(sort)) {
            order += "order by " + sort;
        }
        return order;
    }

    /**
     * mysql 分页
     *
     * @param sql
     * @param table
     * @param where
     * @param order
     * @param pageStart
     * @param pageEnd
     */
    private static void appendSqlForMysql(StringBuilder sql, String table, String where, String order, int pageStart, int pageEnd) {
        sql.append("select * from ").append(table);
        sql.append(where);
        sql.append(order);
        sql.append(" limit ").append(pageStart).append(",").append(pageEnd);
    }


}
