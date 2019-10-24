package com.my.api.generate.core.config.custom;


/**
 * 扩展配置 - 头信息配置
 *
 * @author mengqiang
 */
public class ApiHeaderField {

    /**
     * 字段名
     */
    private String name;

    /**
     * 参数类型
     */
    private String type;

    /**
     * 字段描述
     */
    private String desc;

    /**
     * 是否必须
     */
    private Boolean requiredFlag;

    public static ApiHeaderField createField(String name, String type, String desc) {
        return new ApiHeaderField(name, type, desc, false);
    }

    public static ApiHeaderField createField(String name, String type, String desc, Boolean requiredFlag) {
        return new ApiHeaderField(name, type, desc, requiredFlag);
    }

    public ApiHeaderField(String name, String type, String desc, Boolean requiredFlag) {
        this.name = name;
        this.type = type;
        this.desc = desc;
        this.requiredFlag = requiredFlag;
    }

    public String getName() {
        return name;
    }

    public ApiHeaderField setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public ApiHeaderField setType(String type) {
        this.type = type;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public ApiHeaderField setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public Boolean getRequiredFlag() {
        return requiredFlag;
    }

    public ApiHeaderField setRequiredFlag(Boolean requiredFlag) {
        this.requiredFlag = requiredFlag;
        return this;
    }
}
