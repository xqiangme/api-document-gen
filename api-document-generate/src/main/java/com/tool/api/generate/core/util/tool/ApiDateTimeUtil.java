package com.tool.api.generate.core.util.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author mengqiang
 */
public class ApiDateTimeUtil {
    public static final String DATE_FORMAT_DAY = "yyyy-MM-dd";
    public static final String SHORT_DATE_FORMAT_DAY = "yyyyMMdd";
    public static final String DATE_FORMAT_SECOND = "yyyy-MM-dd HH:mm:ss";

    public static String formatDate(Date date) {
        return format(date, DATE_FORMAT_DAY);
    }

    public static String formatShortDate(Date date) {
        return format(date, SHORT_DATE_FORMAT_DAY);
    }


    public static String formatDateTime(Date date) {
        return format(date, DATE_FORMAT_SECOND);
    }

    public static String format(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }


}