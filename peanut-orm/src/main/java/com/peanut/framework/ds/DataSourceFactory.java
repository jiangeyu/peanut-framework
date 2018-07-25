package com.peanut.framework.ds;

import javax.sql.DataSource;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:11 2018/7/25
 * @desc 数据源工厂
 */
public interface DataSourceFactory {

    DataSource getDataSource();
}
