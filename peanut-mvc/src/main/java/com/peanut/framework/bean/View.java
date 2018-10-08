package com.peanut.framework.bean;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:39 2018/7/25
 * @desc 封装视图对象
 */
@Builder
@Data
public class View {

    private String path;
    private Map<String, Object> data;

    public boolean isRedirect() {
        return path.startsWith("/");
    }
}
