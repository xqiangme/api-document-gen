package com.my.api.generate.core.config;

import com.my.api.generate.core.config.custom.ApiHeaderField;
import com.my.api.generate.core.config.custom.ApiRootResField;
import com.my.api.generate.core.enums.ApiParamTypeEnum;
import com.my.api.generate.core.config.custom.ApiGateWayField;

import java.util.*;

/**
 * 文档生成-非必填参数配置
 *
 * @author mengqiang
 */
public class ApiUnRequiredConfig {

    /**
     * 接口 content-Type
     */
    private String contentType;

    /**
     * 项目扫描地址
     */
    private List<String> projectScanPaths;

    /**
     * 接口包地址
     * <p>
     * 注：生成类型选择包地址情况下必填
     */
    private String apiPackagePath;

    /**
     * 生成文件根路径
     */
    private String generateFilePath;

    /**
     * 参数类型
     * 目前支持类型： "默认类型"，驼峰转下划线
     */
    private ApiParamTypeEnum apiParamType;

    /**
     * 是否生成 json 示例
     */
    private Boolean generateJsonFlag;

    /**
     * 是否输出接口地址
     */
    private Boolean interfacePathShowFlag;

    /**
     * 入参排除-根据参数名称排除
     */
    private Set<String> apiExcludeParamNames;

    /**
     * 入参排除-根据参数全路径排除
     */
    private Set<String> apiExcludeParamClassNames;

    /**
     * 其它配置包路径
     */
    private Set<String> otherSourceCodePaths;

    /**
     * 统一网关接口配置属性
     */
    private ApiGateWayField gateWayField;

    /**
     * 项目根路径
     */
    private String projectRootUrl;

    /**
     * 日志级别
     */
    private String logLevel;

    /**
     * 自定义-返回顶级父类-属性集
     */
    private List<ApiRootResField> rootRespFieldList;

    /**
     * 头信息-属性集
     */
    private List<ApiHeaderField> headerFieldList;


    public ApiUnRequiredConfig setRootRespFieldList(ApiRootResField... resFields) {
        this.rootRespFieldList = Arrays.asList(resFields);
        return this;
    }

    public ApiUnRequiredConfig setApiExcludeParamNames(String... apiExcludeParamNames) {
        this.apiExcludeParamNames = new HashSet<>(Arrays.asList(apiExcludeParamNames));
        return this;
    }

    public ApiUnRequiredConfig setApiExcludeParamClassNames(String... apiExcludeParamClassNames) {
        this.apiExcludeParamClassNames = new HashSet<>(Arrays.asList(apiExcludeParamClassNames));
        return this;
    }

    public ApiUnRequiredConfig setOtherSourceCodePaths(String... otherSourceCodePaths) {
        this.otherSourceCodePaths = new HashSet<>(Arrays.asList(otherSourceCodePaths));
        return this;
    }

    public ApiUnRequiredConfig setHeaderFieldList(ApiHeaderField... headerField) {
        this.headerFieldList = new ArrayList<>(Arrays.asList(headerField));
        return this;
    }


    /**
     * 自动生成 builder 方式 get set 方法
     */
    public String getContentType() {
        return contentType;
    }

    public ApiUnRequiredConfig setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public String getApiPackagePath() {
        return apiPackagePath;
    }

    public List<String> getProjectScanPaths() {
        return projectScanPaths;
    }

    public ApiUnRequiredConfig setProjectScanPaths(List<String> projectScanPaths) {
        this.projectScanPaths = projectScanPaths;
        return this;
    }

    public ApiUnRequiredConfig setApiPackagePath(String apiPackagePath) {
        this.apiPackagePath = apiPackagePath;
        return this;
    }

    public ApiParamTypeEnum getApiParamType() {
        return apiParamType;
    }

    public ApiUnRequiredConfig setApiParamType(ApiParamTypeEnum apiParamType) {
        this.apiParamType = apiParamType;
        return this;
    }

    public Boolean getGenerateJsonFlag() {
        return generateJsonFlag;
    }

    public ApiUnRequiredConfig setGenerateJsonFlag(Boolean generateJsonFlag) {
        this.generateJsonFlag = generateJsonFlag;
        return this;
    }

    public Boolean getInterfacePathShowFlag() {
        return interfacePathShowFlag;
    }

    public ApiUnRequiredConfig setInterfacePathShowFlag(Boolean interfacePathShowFlag) {
        this.interfacePathShowFlag = interfacePathShowFlag;
        return this;
    }

    public Set<String> getApiExcludeParamNames() {
        return apiExcludeParamNames;
    }

    public ApiUnRequiredConfig setApiExcludeParamNames(Set<String> apiExcludeParamNames) {
        this.apiExcludeParamNames = apiExcludeParamNames;
        return this;
    }

    public Set<String> getApiExcludeParamClassNames() {
        return apiExcludeParamClassNames;
    }

    public ApiUnRequiredConfig setApiExcludeParamClassNames(Set<String> apiExcludeParamClassNames) {
        this.apiExcludeParamClassNames = apiExcludeParamClassNames;
        return this;
    }

    public ApiGateWayField getGateWayField() {
        return gateWayField;
    }

    public ApiUnRequiredConfig setGateWayField(ApiGateWayField gateWayField) {
        this.gateWayField = gateWayField;
        return this;
    }

    public String getGenerateFilePath() {
        return generateFilePath;
    }

    public ApiUnRequiredConfig setGenerateFilePath(String generateFilePath) {
        this.generateFilePath = generateFilePath;
        return this;
    }

    public String getProjectRootUrl() {
        return projectRootUrl;
    }

    public ApiUnRequiredConfig setProjectRootUrl(String projectRootUrl) {
        this.projectRootUrl = projectRootUrl;
        return this;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public ApiUnRequiredConfig setLogLevel(String logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    public List<ApiRootResField> getRootRespFieldList() {
        return rootRespFieldList;
    }

    public ApiUnRequiredConfig setRootRespFieldList(List<ApiRootResField> rootRespFieldList) {
        this.rootRespFieldList = rootRespFieldList;
        return this;
    }

    public Set<String> getOtherSourceCodePaths() {
        return otherSourceCodePaths;
    }

    public ApiUnRequiredConfig setOtherSourceCodePaths(Set<String> otherSourceCodePaths) {
        this.otherSourceCodePaths = otherSourceCodePaths;
        return this;
    }

    public List<ApiHeaderField> getHeaderFieldList() {
        return headerFieldList;
    }

    public ApiUnRequiredConfig setHeaderFieldList(List<ApiHeaderField> headerFieldList) {
        this.headerFieldList = headerFieldList;
        return this;
    }
}