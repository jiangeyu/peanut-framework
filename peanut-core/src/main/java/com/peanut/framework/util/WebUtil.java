package com.peanut.framework.util;

import com.peanut.framework.FrameworkConstant;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: <a href="mailto:lingxiao@2dfire.com">凌霄</a>
 * @time: Created in 下午5:37 2018/9/30
 * @desc web操作工具类
 */
public class WebUtil {

    private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);

    public static void writeJSON(HttpServletResponse response, Object data) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding(FrameworkConstant.UTF_8);
            PrintWriter printWriter = response.getWriter();
            printWriter.write(JsonUtil.toJSON(data));
            printWriter.flush();
            printWriter.close();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public static void writeHTML(HttpServletResponse response, Object data) {
        try {
            response.setContentType("application/json");
            logger.info("write html");
            response.setCharacterEncoding(FrameworkConstant.UTF_8);
            PrintWriter printWriter = response.getWriter();
            printWriter.write(JsonUtil.toJSON(data));
            printWriter.flush();
            printWriter.close();
        } catch (Exception e) {
            logger.error("write html error");
            throw new RuntimeException();
        }

    }

    public static Map<String, Object> getRequestParamMap(HttpServletRequest request) {
        Map<String, Object> paramMap = new LinkedHashMap<>();
        try {
            String method = request.getMethod();
            if (method.equalsIgnoreCase("put") || method.equalsIgnoreCase("delete")) {
                String queryString = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
                if (StringUtil.isNotEmpty(queryString)) {
                    String[] qsArray = StringUtil.splitString(queryString, "&");
                    if (ArrayUtil.isNotEmpty(qsArray)) {
                        String paramName = qsArray[0];
                        String paramValue = qsArray[1];
                        if (checkParamName(paramName)) {
                            paramValue = paramMap.get(paramName) + StringUtil.SEPARATOR + paramValue;
                        }
                        paramMap.put(paramName, paramValue);
                    }
                }
            } else {
                Enumeration<String> paramNames = request.getHeaderNames();
                while (paramNames.hasMoreElements()) {
                    String paramName = paramNames.nextElement();
                    if (checkParamName(paramName)) {
                        String[] paramValues = request.getParameterValues(paramName);
                        if (ArrayUtil.isNotEmpty(paramValues)) {
                            if (paramValues.length == 0) {
                                paramMap.put(paramName, paramValues[0]);
                            } else {
                                String paramValue = Arrays.asList(paramValues).stream().collect(Collectors.joining(StringUtil.SEPARATOR));
                                paramMap.put(paramName, paramValue);

                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取请求参数出错");
            throw new RuntimeException(e);
        }
        return paramMap;
    }


    public static boolean checkParamName(String paramName) {
        return !paramName.equals("_");
    }

    /**
     * 转发请求
     *
     * @param path
     * @param request
     * @param response
     */
    public static void forwardRequest(String path, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher(path).forward(request, response);
        } catch (Exception e) {
            logger.error("转发请求出错!", e);
            throw new RuntimeException();
        }
    }

    /**
     * 请求重定向
     *
     * @param path
     * @param request
     * @param response
     */
    public static void redirectRequest(String path, HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect(request.getContextPath() + path);
        } catch (Exception e) {
            logger.error("重定向请求出错！", e);
            throw new RuntimeException(e);
        }
    }

    public static void sendError(int code, String message, HttpServletResponse response) {
        try {
            response.sendError(code, message);
        } catch (Exception e) {
            logger.error("发送错误代码出错!", e);
            throw new RuntimeException();
        }
    }

    /**
     * 是否是ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request) {
        return request.getHeader("X-Requested-With") != null;
    }

    /**
     * 获取请求路径
     *
     * @param request
     * @return
     */
    public static String getRequestPath(HttpServletRequest request) {
        String path = request.getServletPath();
        String pathInfo = StringUtil.defaultIfEmpty(request.getPathInfo(), "");
        return path + pathInfo;
    }

    public static String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        Optional<Cookie> value = Optional.empty();
        if (cookies != null) {
            value = Arrays.asList(cookies).stream().filter(cookie -> name.equals(cookie.getName())).findAny();
        }
        return value.get().getName();
    }

    public static void downloadFile(HttpServletResponse response, String filePath) {
        try {
            String originalFileName = FilenameUtils.getName(filePath);
            String fileName = new String(originalFileName.getBytes("GBK"), "ISO8859_1");
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");

            InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            StreamUtil.copyStream(inputStream, outputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setRedirectUrl(HttpServletRequest request, String sessionKey) {
        if (isAjax(request)) {
            String requestPath = getRequestPath(request);
            request.getSession().setAttribute(sessionKey, requestPath);
        }
    }

    /**
     * 创建验证码
     */
    public static String createCaptcha(HttpServletResponse response) {
        StringBuilder captcha = new StringBuilder();
        try {

            /**
             * 参数初始化,验证码图片的宽度
             */
            int width = 60;
            /**
             * 验证码图片的高度
             */
            int height = 25;
            /**
             * 验证码字符个数
             */
            int codeCount = 4;
            /**
             * 字符横向间距
             */
            int codeX = width / (codeCount + 1);
            /**
             * 字符纵向间距
             */
            int codeY = height - 4;
            /**
             * 字体高度
             */
            int fontHeight = height - 2;
            /**
             * 随机数种子
             */
            int randomSeed = 10;
            /**
             * 验证码中可出现的字符
             */
            char[] codeSequence = {
                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
            };
            /**
             * 创建图像
             */
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bi.createGraphics();
            /**
             * 将图像填充为白色
             */
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);
            /**
             * 设置字体
             */
            g.setFont(new Font("Courier New", Font.BOLD, fontHeight));
            /**
             * 绘制边框
             */
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, width - 1, height - 1);
            /**
             * 产生随机干扰线（160条）
             */
            g.setColor(Color.WHITE);
            /**
             * 创建随机数生成器
             */
            Random random = new Random();
            for (int i = 0; i < 160; i++) {
                int x = random.nextInt(width);
                int y = random.nextInt(height);
                int xl = random.nextInt(12);
                int yl = random.nextInt(12);
                g.drawLine(x, y, x + xl, y + yl);
            }
            /**
             * 生成随机验证码
             */
            int red, green, blue;
            for (int i = 0; i < codeCount; i++) {

                /**
                 * 获取随机验证码
                 */
                String validateCode = String.valueOf(codeSequence[random.nextInt(randomSeed)]);

                /**
                 * 随机构造颜色值
                 */
                red = random.nextInt(255);
                green = random.nextInt(255);
                blue = random.nextInt(255);
                /**
                 * 将带有颜色的验证码绘制到图像中
                 */
                g.setColor(new Color(red, green, blue));
                g.drawString(validateCode, (i + 1) * codeX - 6, codeY);
                /**
                 * 将产生的随机数拼接起来
                 */
                captcha.append(validateCode);
            }
            /**
             * 禁止图像缓存
             */
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            /**
             * 设置响应类型为 JPEG 图片
             */
            response.setContentType("image/jpeg");

            /**
             * 将缓冲图像写到 Servlet 输出流中
             */
            ServletOutputStream sos = response.getOutputStream();
            ImageIO.write(bi, "jpeg", sos);
            sos.close();
        } catch (Exception e) {
            logger.error("创建验证码出错！", e);
            throw new RuntimeException(e);
        }
        return captcha.toString();
    }

    /**
     * 是否为 IE 浏览器
     */
    public boolean isIE(HttpServletRequest request) {
        String agent = request.getHeader("User-Agent");
        return agent != null && agent.contains("MSIE");
    }
}
