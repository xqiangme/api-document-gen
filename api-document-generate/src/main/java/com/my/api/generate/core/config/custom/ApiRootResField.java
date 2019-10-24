package com.my.api.generate.core.config.custom;


/**
 * 扩展配置 - 返回参数-自定义顶级对象属性
 *
 * @author mengqiang
 */
public class ApiRootResField {

    /**
     * 字段名
     */
    private String name;

    /**
     * 参数类型
     */
    private Class typeClass;

    /**
     * 字段描述
     */
    private String desc;

    /**
     * 是否为父类属性
     */
    private Boolean isParent;


    public static ApiRootResField createField(String name, Class typeClass, String desc) {
        return new ApiRootResField(name, typeClass, desc, false);
    }

    public static ApiRootResField createField(String name, Class typeClass, String desc, Boolean isParent) {
        return new ApiRootResField(name, typeClass, desc, isParent);
    }

    public ApiRootResField(String name, Class typeClass, String desc, Boolean isParent) {
        this.name = name;
        this.typeClass = typeClass;
        this.desc = desc;
        this.isParent = isParent;
    }

    public String getName() {
        return name;
    }

    public ApiRootResField setName(String name) {
        this.name = name;
        return this;
    }

    public Class getTypeClass() {
        return typeClass;
    }

    public ApiRootResField setTypeClass(Class typeClass) {
        this.typeClass = typeClass;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public ApiRootResField setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public Boolean getParent() {
        return isParent;
    }

    public ApiRootResField setParent(Boolean parent) {
        isParent = parent;
        return this;
    }
}
