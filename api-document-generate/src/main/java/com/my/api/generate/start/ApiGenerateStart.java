package com.my.api.generate.start;

import com.my.api.generate.core.config.ApiConfig;
import com.my.api.generate.core.util.helper.ApiGenerateHelper;
import com.my.api.generate.core.util.tool.ApiFilePathUtil;
import com.my.api.generate.core.util.tool.ApiLogUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

/**
 * 接口文档生成启动类
 *
 * @author mengqiang
 */
public class ApiGenerateStart {

    /**
     * 执行入口
     *
     * @author mengqiang
     */
    public static void run(ApiConfig config) {
        ApiLogUtil.generateStart();
        try {
            //校验必填参数
            ApiGenerateHelper.checkRequiredConfig(ApiGenerateStart.class, config);
            //项目根路径
            String projectRootPath = ApiFilePathUtil.getRootPath();
            //非必填参数默认值转换
            ApiGenerateHelper.convertDefaultConfig(projectRootPath, config);
            ApiLogUtil.info("自动获取的项目根路径 {0}", projectRootPath);
            if (config.getConfirmRootPath()) {
                ApiLogUtil.newLine();
                ApiLogUtil.info("请确认获取的根路径是否正常，输入回车将继续执行，输入新路径将替换当前获取到的扫描路径 ！");
                Scanner scanner = new Scanner(System.in);
                String scannerPath = scanner.nextLine();
                if (StringUtils.isNotBlank(scannerPath)) {
                    //以输入的路径替换
                    projectRootPath = scannerPath;
                    //更新默认生成路径
                    config.setGenerateFilePath(projectRootPath);
                    ApiLogUtil.info("项目根路径已替换为 {0}", projectRootPath);
                }
            }
            //执行公共方法
            ApiGenerateHelper.doRun(projectRootPath, config);
        } catch (Exception e) {
            //打印异常信息
            ApiLogUtil.printlnError(e);
        }
    }
}