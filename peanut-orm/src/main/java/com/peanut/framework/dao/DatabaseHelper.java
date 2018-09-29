package com.peanut.framework.dao;

import com.peanut.framework.ConfigHelper;
import com.peanut.framework.InstanceFactory;
import com.peanut.framework.ds.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

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

    public static Connection getCoonection() {
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
        Connection connection = getCoonection();
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
        Connection connection = getCoonection();
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
        Connection connection = getCoonection();
        try {
            connection.rollback();
        } catch (SQLException exception) {
            logger.error("");
            new RuntimeException("");
        } finally {
            connContainer.remove();
        }
    }

}
