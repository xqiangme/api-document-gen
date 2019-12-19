package com.tool.api.generate.core.util.helper;

import com.tool.api.generate.core.constants.ControllerConstants;
import com.tool.api.generate.core.constants.ValidationConstant;
import com.tool.api.generate.core.util.tool.ApiStringPool;
import com.tool.api.generate.core.config.ApiConfig;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * controller 相关辅助工具类
 *
 * @author mengqiang
 */
public class ApiControllerHelper {

    /**
     * 获取基础访问路径
     *
     * @param clazz
     * @return
     */
    public static String getApiRootUrl(JavaClass clazz, ApiConfig config) {
        //controller 根路径
        StringBuilder controllerRootUrl = new StringBuilder();
        for (JavaAnnotation annotation : clazz.getAnnotations()) {
            String annotationName = annotation.getType().getName();
            if (ControllerConstants.REQUEST_MAPPING_STR.equals(annotationName)) {
                String baseUrl = annotation.getNamedParameter(ValidationConstant.VALUE_STR).toString().replaceAll("\"", "");
                if (!baseUrl.startsWith(ApiStringPool.SLASH)) {
                    controllerRootUrl.append(ApiStringPool.SLASH);
                }
                controllerRootUrl.append(baseUrl);
            }
        }
        return String.valueOf(controllerRootUrl);
    }

    /**
     * 获取项目访问基础路径
     *
     * @return
     */
    public static String getProjectRootUrl(ApiConfig config) {
        //项目根路径配置
        StringBuilder baseUrlBuilder = new StringBuilder();
        if (StringUtils.isNotBlank(config.getProjectRootUrl())) {
            if (!config.getProjectRootUrl().startsWith(ApiStringPool.SLASH)) {
                baseUrlBuilder.append(ApiStringPool.SLASH);
            }
            baseUrlBuilder.append(config.getProjectRootUrl());
        }
        return String.valueOf(baseUrlBuilder);
    }

    /**
     * 检测是否为controller 类
     *
     * @param clazz
     * @return
     */
    public static boolean isController(JavaClass clazz) {
        List<JavaAnnotation> classAnnotations = clazz.getAnnotations();
        for (JavaAnnotation annotation : classAnnotations) {
            String annotationName = annotation.getType().getName();
            if (ControllerConstants.CONTROLLER_STR.equals(annotationName)) {
                return true;
            }
            if (ControllerConstants.REST_CONTROLLER_STR.equals(annotationName)) {
                return true;
            }
        }
        return false;
    }


}