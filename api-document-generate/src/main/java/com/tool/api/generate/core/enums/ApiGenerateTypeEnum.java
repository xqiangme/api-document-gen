package com.tool.api.generate.core.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 生成类型枚举
 *
 * @author mengqiang
 */
@Getter
public enum ApiGenerateTypeEnum {

    /**
     * 生成类型
     */
    PACKAGE(1, "根据包地址生成接口文档"),
    CLAZZ(2, "根据类地址生成接口文档"),
    ;

    private Integer type;

    private String desc;

    ApiGenerateTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static ApiGenerateTypeEnum getGenerateType(String type) {
        if (StringUtils.isBlank(type)) {
            return null;
        }
        for (ApiGenerateTypeEnum typeEnum : ApiGenerateTypeEnum.values()) {
            if (String.valueOf(typeEnum.getType()).equals(type)) {
                return typeEnum;
            }
        }
        return null;
    }

}
