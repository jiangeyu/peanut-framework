package com.peanut.framework;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 上午10:00 2018/9/30
 * @desc 处理器映射
 */
public interface HandlerMapping {

    Handler getHandler(String currentRequestMethod,String currentRequestPath);

}
