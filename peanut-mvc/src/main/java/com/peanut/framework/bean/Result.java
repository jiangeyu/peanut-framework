package com.peanut.framework.bean;

import lombok.Builder;
import lombok.Data;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午11:40 2018/7/25
 * @desc
 */
@Builder
@Data
public class Result {

    private boolean success;

    private int error;

    private Object data;
}
