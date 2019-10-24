package com.my.api.generate.core.config;

import com.my.api.generate.core.enums.ApiGenerateTypeEnum;

import java.util.Arrays;
import java.util.List;

/**
 * 文档生成-核心配置类
 *
 * @author mengqiang
 */
public class ApiConfig extends ApiUnRequiredConfig {

    /**
     * 是否开启根路径确认
     * 默认 是
     */
    private Boolean confirmRootPath;
    /**
     * 生成类型
     */
    private ApiGenerateTypeEnum generateType;

    /**
     * 接口包地址
     * <p>
     * 注：生成类型选择包地址情况下必填
     */
    private String apiPackage;

    /**
     * 接口的类集合
     */
    private List<String> apiClassNameList;

    /**
     * 接口的类集合
     */
    private List<String> apiClassPathList;


    public ApiConfig setApiClassNameList(String... className) {
        this.apiClassNameList = Arrays.asList(className);
        return this;
    }

    /**
     * 自动生成 builder 方式 get set 方法
     */
    public ApiGenerateTypeEnum getGenerateType() {
        return generateType;
    }

    public Boolean getConfirmRootPath() {
        return confirmRootPath;
    }

    public ApiConfig setConfirmRootPath(Boolean confirmRootPath) {
        this.confirmRootPath = confirmRootPath;
        return this;
    }

    public List<String> getApiClassPathList() {
        return apiClassPathList;
    }

    public ApiConfig setApiClassPathList(List<String> apiClassPathList) {
        this.apiClassPathList = apiClassPathList;
        return this;
    }

    public ApiConfig setGenerateType(ApiGenerateTypeEnum generateType) {
        this.generateType = generateType;
        return this;
    }

    public String getApiPackage() {
        return apiPackage;
    }

    public ApiConfig setApiPackage(String apiPackage) {
        this.apiPackage = apiPackage;
        return this;
    }

    public List<String> getApiClassNameList() {
        return apiClassNameList;
    }

    public ApiConfig setApiClassNameList(List<String> apiClassList) {
        this.apiClassNameList = apiClassList;
        return this;
    }
}