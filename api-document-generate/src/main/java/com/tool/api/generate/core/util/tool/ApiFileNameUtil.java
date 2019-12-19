package com.tool.api.generate.core.util.tool;

import java.util.Date;

/**
 * api 接口文档名称生成工具类
 *
 * @author mengqiang
 */
public class ApiFileNameUtil {

    private static final String BASE_FILE_NAME = "/Api-generate-";
    private static final String SUFFIX = ".md";
    private static final String FORMAT = "MMddHHmmss";
    public static final String GENERATE = "generate";

    /**
     * 生成api 接口文档名称
     */
    public static String getFileName() {
        return BASE_FILE_NAME.concat(ApiDateTimeUtil.format(new Date(), FORMAT)).concat(SUFFIX);
    }


    /**
     * 拼接地址 xml 形式执行 ，生成文件地址
     */
    public static String getGenerate(String path) {
        if (!path.endsWith(ApiStringPool.SLASH)) {
            return path.concat(ApiStringPool.SLASH).concat(GENERATE);
        }
        return path.concat(GENERATE);
    }

}