package com.peanut.framework.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 上午12:08 2018/9/23
 * @desc
 */
public class PropsUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropsUtil.class);

    /**
     * 加载属性文件
     *
     * @param propsPath
     * @return
     */
    public static Properties loadProps(String propsPath) {
        Properties props = new Properties();
        InputStream is = null;
        try {
            if (StringUtils.isEmpty(propsPath)) {
                throw new IllegalArgumentException();
            }
            String suffix = ".properties";
            if (propsPath.lastIndexOf(suffix) == -1) {
                propsPath += suffix;
            }
            is = ClassUtil.getClassLoader().getResourceAsStream(propsPath);
            if (is != null) {
                props.load(is);
            }

        } catch (Exception e) {
            logger.error("加载属性文件出错！ ", e);
            throw new RuntimeException();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                logger.error("关闭流出错! ", e);
            }
        }
        return props;
    }

    public static Map<String, String> loadPropsToMap(String propsPath) {
        final Properties properties = loadProps(propsPath);
        Map<String, String> map = properties.stringPropertyNames()
                .stream()
                .collect(Collectors.toMap(k -> k, t-> properties.getProperty(t), (v1, v2) -> v1));
        return map;
    }




}
