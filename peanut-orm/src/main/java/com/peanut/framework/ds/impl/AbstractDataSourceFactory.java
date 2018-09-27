package com.peanut.framework.ds.impl;

import com.peanut.framework.ConfigHelper;
import com.peanut.framework.ds.DataSourceFactory;

import javax.sql.DataSource;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:18 2018/7/25
 * @desc
 */
public abstract class AbstractDataSourceFactory<T extends DataSource> implements DataSourceFactory {

    protected final String driver = ConfigHelper.getString("com.github.jdbc.driver");
    protected final String url = ConfigHelper.getString("com.github.jdbc.url");
    protected final String username = ConfigHelper.getString("com.github.jdbc.username");
    protected final String password = ConfigHelper.getString("com.github.jdbc.password");


    public final T getDataSource() {
        T ds = createDataSource();
        setDriver(ds, driver);
        setUrl(ds, url);
        setUsername(ds, username);
        setPassword(ds, password);
        setAdvancedConfig(ds);
        return ds;
    }

    /**
     * 创建数据源
     *
     * @return
     */
    public abstract T createDataSource();

    public abstract void setDriver(T ds, String driver);

    public abstract void setUrl(T ds, String url);

    public abstract void setUsername(T ds, String username);

    public abstract void setPassword(T ds, String password);

    public abstract void setAdvancedConfig(T ds);
}
