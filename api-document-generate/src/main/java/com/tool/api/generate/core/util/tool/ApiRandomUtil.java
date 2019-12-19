package com.tool.api.generate.core.util.tool;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * 生成 随机值
 *
 * @author mengqiang
 */
public class ApiRandomUtil {

    private static Random random = new Random();
    /**
     * 小数格式
     */
    public static String FORMAT = "0.00";
    /**
     * 随机数字
     */
    private static final String BASE_NUMBER = "0123456789";
    /**
     * 随机字符串常量
     */
    private static final String BASE_CHAR = "abcdefghijklmnopqrstuvwxyz";

    /**
     * 随机数字拼接字符串
     */
    private static final String BASE_CHAR_NUMBER = BASE_CHAR + BASE_NUMBER;

    /**
     * 随机 int 类型 数字
     */
    public static int randomInt(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    /**
     * 随机 int 类型 数字
     */
    public static int randomInt(int limit) {
        return random.nextInt(limit);
    }

    /**
     * 随机 int 类型 数字
     */
    public static int randomInt() {
        return random.nextInt();
    }

    /**
     * 随机 long 类型 数字
     */
    public static long randomLong() {
        return random.nextLong();
    }

    /**
     * 随机 long 类型 数字
     */
    public static long randomLong(long min, long max) {
        long rangeLong = min + (((long) (new Random().nextDouble() * (max - min))));
        return rangeLong;
    }

    /**
     * 随机字符串
     */
    public static String randomString(int length) {
        return randomString(BASE_CHAR_NUMBER, length);
    }

    /**
     * 随机字符串
     */
    public static String randomString(String baseString, int length) {
        StringBuffer sb = new StringBuffer();
        if (length < 1) {
            length = 1;
        }
        int baseLength = baseString.length();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(baseLength);
            sb.append(baseString.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 随机小数
     */
    public static double randomDouble(final double min, final double max) {
        return new Double(new DecimalFormat(FORMAT).format(min + ((max - min) * random.nextDouble())));
    }

    /**
     * 随机小数
     */
    public static double randomDouble() {
        return new Double(new DecimalFormat(FORMAT).format(randomDouble(1.00, 1000.00)));
    }

}
