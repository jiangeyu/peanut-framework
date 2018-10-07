package com.peanut.framework.util;

import com.peanut.framework.FrameworkConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 上午12:18 2018/10/8
 * @desc 编码与解码操作工具类
 */
public class CodecUtil {

    private static final Logger logger = LoggerFactory.getLogger(CodecUtil.class);

    public static String encodeURL(String url) {
        String target;
        try {
            target = URLEncoder.encode(url, FrameworkConstant.UTF_8);
        } catch (Exception e) {
            logger.error("编码出错", e);
            throw new RuntimeException(e);
        }
        return target;
    }


    public static String decodeURL(String url) {
        String target;
        try {
            target = URLDecoder.decode(url, FrameworkConstant.UTF_8);
        } catch (Exception e) {
            logger.error("编码出错", e);
            throw new RuntimeException(e);
        }
        return target;
    }
}
