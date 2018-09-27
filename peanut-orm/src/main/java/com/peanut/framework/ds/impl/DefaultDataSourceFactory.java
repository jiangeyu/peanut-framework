package com.peanut.framework.ds.impl;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:18 2018/7/25
 * @desc
 */
public class DefaultDataSourceFactory extends AbstractDataSourceFactory<BasicDataSource> {

    @Override
    public BasicDataSource createDataSource() {
        return new BasicDataSource();
    }

    @Override
    public void setDriver(BasicDataSource ds, String driver) {
        ds.setDriverClassName(driver);
    }

    @Override
    public void setUrl(BasicDataSource ds, String url) {
        ds.setUrl(url);
    }

    @Override
    public void setUsername(BasicDataSource ds, String username) {
        ds.setUsername(username);
    }

    @Override
    public void setPassword(BasicDataSource ds, String password) {
        ds.setPassword(password);
    }

    @Override
    public void setAdvancedConfig(BasicDataSource ds) {
        ds.setValidationQuery("select 1 from dual");
    }
}
