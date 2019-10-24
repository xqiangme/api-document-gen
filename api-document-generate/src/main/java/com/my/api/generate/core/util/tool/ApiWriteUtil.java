package com.my.api.generate.core.util.tool;

import com.my.api.generate.core.exception.ApiGenerateException;
import freemarker.template.Template;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * 根据模板写出文件工具
 *
 * @author mengqiang
 */
public class ApiWriteUtil {

    private static final String CHAR_SET = ApiStringPool.UTF_8;

    /**
     * 根据模板输出文件
     */
    public static void write(Template template, Object data,
                             String destFilePath) {
        Writer out = null;
        try {
            File destFile = new File(destFilePath);
            generateFolder(destFile.getParent());
            if (!destFile.exists()) {
                boolean flag = destFile.createNewFile();
                if (!flag) {
                    throw new ApiGenerateException("文件无法被创建");
                }
            }
            out = new OutputStreamWriter(new FileOutputStream(destFilePath), CHAR_SET);
            template.process(data, out);
            out.flush();

        } catch (Exception e) {
            ApiLogUtil.debug("文件写出异常了 {0}" , ExceptionUtils.getStackTrace(e));
            throw new ApiGenerateException("文件写出异常,写出路径不存在或文件无法被创建！destFilePath：{0}", destFilePath);
        } finally {
            if (null != out) {
                try {
                    out.close();
                    ApiLogUtil.debug("【文件写出结束】关闭流资源。。。");
                } catch (Exception e) {
                    throw new ApiGenerateException("文件写出异常,关闭流异常了！");
                }
            }
        }
    }

    /**
     * 生成文件夹
     *
     * @param folderPath 文件夹路径
     */
    private static void generateFolder(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }
}