package com.my.api.generate.core.util.tool;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

/**
 * freeMarker 模板读取工具
 *
 * @author mengqiang
 */
public class ApiTemplateUtils {

    private static final Configuration CONFIG = new Configuration();
    /**
     * 模板文件读取路径
     */
    private static final String PATH = "/api_template";
    private static final int MAX_STRONG_SIZE = 100;
    private static final int MAX_SOFT_SIZE = 2500;

    static {
        //指定加载模板所在的路径
        CONFIG.setTemplateLoader(new ClassTemplateLoader(ApiTemplateUtils.class, PATH));
        CONFIG.setDefaultEncoding(ApiStringPool.UTF_8);
        CONFIG.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        CONFIG.setCacheStorage(new freemarker.cache.MruCacheStorage(MAX_STRONG_SIZE, MAX_SOFT_SIZE));
    }

    private ApiTemplateUtils() {
    }

    public static Configuration getConfiguration() {
        return CONFIG;
    }

    public static void loggerConfig() {
        try {
            freemarker.log.Logger.selectLoggerLibrary(freemarker.log.Logger.LIBRARY_NONE);
            ApiLogUtil.debug("加载[freemarker]成功");
        } catch (ClassNotFoundException e) {
            ApiLogUtil.error("加载[freemarker]失败，未找到类", e);
        }
    }

    public static Template getTemplate(String templateName) {
        try {
            return CONFIG.getTemplate(templateName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
