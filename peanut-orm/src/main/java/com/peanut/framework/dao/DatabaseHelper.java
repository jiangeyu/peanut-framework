package com.peanut.framework.dao;

import com.peanut.framework.ConfigHelper;
import com.peanut.framework.InstanceFactory;
import com.peanut.framework.ds.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:34 2018/9/27
 * @desc 封装数据相关操作
 */
public class DatabaseHelper {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);

    private static final ThreadLocal<Connection> connContainer = new ThreadLocal<>();

    private static final DataSourceFactory dataSourceFactory = InstanceFactory.getDataSourceFactory();

    private static final DataAccessor dataAccessor = InstanceFactory.getDataAccessor();

    private static final String databaseType = ConfigHelper.getString("com.github.framework.jdbc.type");

    private static String getDatabaseType() {
        return databaseType;
    }

    public static DataSource getDataSource() {
        return dataSourceFactory.getDataSource();
    }

    public static Connection getConnection() {
        Connection connection;
        try {
            connection = connContainer.get();
            if (connection == null) {
                connection = getDataSource().getConnection();
                if (connection != null) {
                    connContainer.set(connection);
                }
            }
        } catch (Exception e) {
            logger.error("get database connection error");
            throw new RuntimeException("get database connection error");
        }
        return connection;
    }

    public static void startTransaction() {
        Connection connection = getConnection();
        if(connection != null) {
            try {
               connection.setAutoCommit(false);
            }  catch (SQLException exception) {
                logger.error("");
                throw new RuntimeException("");
            }finally {
                connContainer.set(connection);
            }
        }

    }

    public static void commitTransaction() {
        Connection connection = getConnection();
        try {
            connection.commit();
        } catch (SQLException exception) {
            logger.error("");
            new RuntimeException("");
        } finally {
            connContainer.remove();
        }
    }

    public static void roolbackTransction() {
        Connection connection = getConnection();
        try {
            connection.rollback();
        } catch (SQLException exception) {
            logger.error("");
            new RuntimeException("");
        } finally {
            connContainer.remove();
        }
    }


    /**
     * 根据 SQL 语句查询 Entity
     */
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        return dataAccessor.queryEntity(entityClass, sql, params);
    }

    /**
     * 根据 SQL 语句查询 Entity 列表
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        return dataAccessor.queryEntityList(entityClass, sql, params);
    }

    /**
     * 根据 SQL 语句查询 Entity 映射（Field Name => Field Value）
     */
    public static <K, V> Map<K, V> queryEntityMap(Class<V> entityClass, String sql, Object... params) {
        return dataAccessor.queryEntityMap(entityClass, sql, params);
    }

    /**
     * 根据 SQL 语句查询 Array 格式的字段（单条记录）
     */
    public static Object[] queryArray(String sql, Object... params) {
        return dataAccessor.queryArray(sql, params);
    }

    /**
     * 根据 SQL 语句查询 Array 格式的字段列表（多条记录）
     */
    public static List<Object[]> queryArrayList(String sql, Object... params) {
        return dataAccessor.queryArrayList(sql, params);
    }

    /**
     * 根据 SQL 语句查询 Map 格式的字段（单条记录）
     */
    public static Map<String, Object> queryMap(String sql, Object... params) {
        return dataAccessor.queryMap(sql, params);
    }

    /**
     * 根据 SQL 语句查询 Map 格式的字段列表（多条记录）
     */
    public static List<Map<String, Object>> queryMapList(String sql, Object... params) {
        return dataAccessor.queryMapList(sql, params);
    }

    /**
     * 根据 SQL 语句查询指定字段（单条记录）
     */
    public static <T> T queryColumn(String sql, Object... params) {
        return dataAccessor.queryColumn(sql, params);
    }

    /**
     * 根据 SQL 语句查询指定字段列表（多条记录）
     */
    public static <T> List<T> queryColumnList(String sql, Object... params) {
        return dataAccessor.queryColumnList(sql, params);
    }

    /**
     * 根据 SQL 语句查询指定字段映射（多条记录）
     */
    public static <T> Map<T, Map<String, Object>> queryColumnMap(String column, String sql, Object... params) {
        return dataAccessor.queryColumnMap(column, sql, params);
    }

    /**
     * 根据 SQL 语句查询记录条数
     */
    public static long queryCount(String sql, Object... params) {
        return dataAccessor.queryCount(sql, params);
    }

    /**
     * 执行更新语句（包括：update、insert、delete）
     */
    public static int update(String sql, Object... params) {
        return dataAccessor.update(sql, params);
    }

    /**
     * 执行插入语句，返回插入后的主键
     */
    public static Serializable insertReturnPK(String sql, Object... params) {
        return dataAccessor.insertReturnPK(sql, params);
    }

}
