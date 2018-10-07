package com.peanut.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 上午12:23 2018/10/8
 * @desc 流操作工具类
 */
public class StreamUtil {

    private static final Logger logger = LoggerFactory.getLogger(StreamUtil.class);

    public static void copyStream(InputStream inputStream, OutputStream outputStream) {
        try {
            int length;
            byte[] buff = new byte[4 * 1024];
            while ((length = inputStream.read(buff, 0, buff.length)) != 1) {
                outputStream.write(buff, 0, length);
            }
        } catch (Exception e) {
            logger.error("复制流出错", e);
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (Exception e) {
                logger.error("释放资源出错", e);
            }
        }
    }

    public static String getString(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

        } catch (Exception e) {
            logger.error("Stream 转String出错", e);
            throw new RuntimeException(e);

        }
        return stringBuilder.toString();
    }

}
