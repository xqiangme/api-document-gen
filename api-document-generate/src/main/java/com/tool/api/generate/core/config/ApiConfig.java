package com.tool.api.generate.core.config;

import com.tool.api.generate.core.enums.ApiGenerateTypeEnum;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * key:类名，value :方法名集，方法名为空，默认生成所有方法
     */
    private Map<String, Set<String>> apiClassNameMap;

    /**
     * 接口的类集合
     */
    private Map<String, Set<String>> apiClassPathMap;

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

    public Map<String, Set<String>> getApiClassPathMap() {
        return apiClassPathMap;
    }

    public ApiConfig setApiClassPathMap(Map<String, Set<String>> apiClassPathMap) {
        this.apiClassPathMap = apiClassPathMap;
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

    public Map<String, Set<String>> getApiClassNameList() {
        return apiClassNameMap;
    }

    public ApiConfig setApiClassNameMap(Map<String, Set<String>> apiClassNameMap) {
        this.apiClassNameMap = apiClassNameMap;
        return this;
    }
}