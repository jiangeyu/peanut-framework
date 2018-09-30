package com.peanut.framework;

import lombok.Builder;
import lombok.Data;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 上午10:15 2018/9/30
 * @desc
 */
@Builder
@Data
public class Request {

    private String requestMethod;

    private String requestPath;
}
