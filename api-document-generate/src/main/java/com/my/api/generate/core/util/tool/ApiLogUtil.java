package com.my.api.generate.core.util.tool;

import com.my.api.generate.core.exception.ApiGenerateException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.text.MessageFormat;

/**
 * 日志输出工具
 * 注：由于仅用于生成文档工具，暂时使用 System.out 日志
 *
 * @author mengqiang
 */
public class ApiLogUtil {

    private static final int DEBUG = 1;
    private static final int INFO = 2;
    private static final int WARN = 2;
    private static final int ERROR = 2;

    private static final String INFO_STR = "info";
    private static final String DEBUG_STR = "debug";
    private static final String WARN_STR = "warn";
    private static final String ERROR_STR = "error";
    /**
     * 默认级别
     */
    public static int LEVEL = 2;

    public static void debug(String mess, Object... args) {
        if (LEVEL <= DEBUG) {
            System.out.println(MessageFormat.format(">>> " + mess, args));
        }
    }

    public static void info(String mess, Object... args) {
        if (LEVEL <= INFO) {
            System.out.println(MessageFormat.format(">>> " + mess, args));
        }
    }

    public static void warn(String mess, Object... args) {
        if (LEVEL <= WARN) {
            System.err.println(MessageFormat.format(">>>【警告】>>> " + mess, args));
        }
    }

    public static void error(String mess, Object... args) {
        if (LEVEL <= ERROR) {
            ApiLogUtil.newLine();
            System.err.println(MessageFormat.format(">>>【异常】>>> " + mess, args));
        }
    }

    public static void newLine() {
        System.out.println();
    }

    public static void newDebugLine() {
        if (LEVEL <= DEBUG) {
            System.out.println();
        }
    }

    public static void generateStart() {
        ApiLogUtil.newLine();
        ApiLogUtil.info("********************** ^_^ 接口文档生成开始 ^_^ **********************");
    }

    public static void generateError() {
        if (LEVEL <= ERROR) {
            ApiLogUtil.newLine();
            System.err.println(">>> ********************** T_T 接口文档生成失败 T_T **********************");
        }
    }

    public static void generateEnd() {
        ApiLogUtil.newLine();
        ApiLogUtil.info("********************** ^_^ 接口文档生成结束 ^_^ **********************");
    }

    public static void printlnError(Exception e) {
        ApiLogUtil.generateError();
        if (e instanceof ApiGenerateException) {
            //自定义异常-仅抛出原因
            ApiLogUtil.error("失败原因： {0}", e.getMessage());
        } else {
            //非自定义异常-打印堆栈
            ApiLogUtil.error("失败原因： 未知异常 stack={0}", ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 根据名称匹配参数级别
     */
    public static Integer getLevelByName(String levelName) {
        if (StringUtils.isBlank(levelName)) {
            return LEVEL;
        }
        if (INFO_STR.equalsIgnoreCase(levelName)) {
            return INFO;
        }
        if (DEBUG_STR.equalsIgnoreCase(levelName)) {
            return DEBUG;
        }
        if (WARN_STR.equalsIgnoreCase(levelName)) {
            return WARN;
        }
        if (ERROR_STR.equalsIgnoreCase(levelName)) {
            return ERROR;
        }
        return LEVEL;
    }

}