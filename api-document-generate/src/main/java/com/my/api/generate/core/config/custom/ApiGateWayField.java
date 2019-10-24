package com.my.api.generate.core.config.custom;


/**
 * 扩展配置 - 统一网关接口配置属性
 *
 * @author mengqiang
 */
public class ApiGateWayField {

    /**
     * 自定义注解全路径名
     */
    private String annotationName;

    /**
     * 自定义注解-属性名
     * 注：默认 method
     */
    private String annotationFieldName;

    public static ApiGateWayField createField(String annotationName) {
        return new ApiGateWayField(annotationName);
    }

    public static ApiGateWayField createField(String annotationName, String annotationFieldName) {
        return new ApiGateWayField(annotationName, annotationFieldName);
    }

    public ApiGateWayField(String annotationName) {
        this.annotationName = annotationName;
    }

    public ApiGateWayField(String annotationName, String annotationFieldName) {
        this.annotationName = annotationName;
        this.annotationFieldName = annotationFieldName;
    }

    public String getAnnotationName() {
        return annotationName;
    }

    public ApiGateWayField setAnnotationName(String annotationName) {
        this.annotationName = annotationName;
        return this;
    }

    public String getAnnotationFieldName() {
        return annotationFieldName;
    }

    public ApiGateWayField setAnnotationFieldName(String annotationFieldName) {
        this.annotationFieldName = annotationFieldName;
        return this;
    }
}
