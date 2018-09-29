package com.peanut.framework.dao;

import com.peanut.framework.InstanceFactory;
import com.peanut.framework.ds.DataSourceFactory;

import java.sql.Connection;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:34 2018/9/27
 * @desc
 */
public class DatabaseHelper {

    private static final ThreadLocal<Connection> connContainer = new ThreadLocal<>();

    private static final DataSourceFactory dataSourceFactory = InstanceFactory.getDataSourceFactory();
}
