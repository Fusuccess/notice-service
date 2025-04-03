package com.fusuccess.untils;

/**
 * 日期工具类
 * author: fusuccess
 * email: fu2000520@gmail.com
 */
public class DateUtils {

    /**
     * 获取当前北京时间
     * @return 当前北京时间的字符串表示
     */
    public static String getBeijingTime() {
        java.util.Date date = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+8"));
        return sdf.format(date);
    }
}
