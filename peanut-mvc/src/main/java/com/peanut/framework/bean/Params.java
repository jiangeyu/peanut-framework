package com.peanut.framework.bean;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:40 2018/7/25
 * @desc
 */
@Builder
@Data
public class Params {
    private final Map<String, Object> fieldMap;

    public Params(Map<String, Object> fieldMap) {
        this.fieldMap = fieldMap;
    }
}
