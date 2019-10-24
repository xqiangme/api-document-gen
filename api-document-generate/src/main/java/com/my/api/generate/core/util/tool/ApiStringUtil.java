package com.my.api.generate.core.util.tool;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具
 *
 * @author mengqiang
 */
public class ApiStringUtil {

    /**
     * 替换中括号 [] => ""
     */
    public static String replaceInBracket(String str) {
        if (StringUtils.isBlank(str)) {
            return ApiStringPool.EMPTY;
        }
        //替换字符
        String content = str
                .trim()
                .replaceAll("\\[", "")
                .replaceAll("\\]", "");
        return content;
    }
    /**
     * 过滤特殊字符
     */
    public static String replaceContent(String str) {
        if (StringUtils.isBlank(str)) {
            return ApiStringPool.EMPTY;
        }
        //替换字符
        String content = str
                .trim()
                .replaceAll("\\/\\*\\*", "")
                .replaceAll("\\*\\/", "")
                .replaceAll("\\*", "")
                .replaceAll(" +", " ");
        return content;
    }


    /**
     * 过滤大括号
     */
    public static String replaceMaxBracket(String str) {
        if (StringUtils.isBlank(str)) {
            return ApiStringPool.EMPTY;
        }
        //替换字符
        String content = str
                .trim()
                .replaceAll("\\{", "")
                .replaceAll("\\}", "");
        return content;
    }

    /**
     * 替换//   => ""
     */
    public static String replaceSlash(String str) {
        if (StringUtils.isBlank(str)) {
            return ApiStringPool.EMPTY;
        }
        //替换字符
        String content = str
                .trim()
                .replaceAll("\\\\", "");
        return content;
    }

    /**
     * 获取带引号的字符
     */
    public static String getQuotationStr(String content) {
        if (StringUtils.isBlank(content)) {
            return "\"\"";
        }
        return ApiStringPool.QUOTATION_MARKS.concat(content).concat(ApiStringPool.QUOTATION_MARKS);
    }

    /**
     * 移除结尾的 逗号
     */
    public static String removeEndComma(String content) {
        if (!content.endsWith(ApiStringPool.COMMA)) {
            return content;
        }
        content = content.substring(0, content.lastIndexOf(ApiStringPool.COMMA));
        if (content.endsWith(ApiStringPool.COMMA)) {
            content = removeEndComma(content);
        }
        return content;
    }

}
