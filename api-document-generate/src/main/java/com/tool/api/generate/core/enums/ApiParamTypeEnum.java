package com.tool.api.generate.core.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 生成类型枚举
 *
 * @author mengqiang
 */
@Getter
public enum ApiParamTypeEnum {

    /**
     * 参数类型
     */
    COMMON(1, "默认类型"),
    SNAKE_CASE(2, "驼峰转下划线"),
    ;

    private Integer type;

    private String desc;

    ApiParamTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static ApiParamTypeEnum getParamType(String type) {
        if (StringUtils.isBlank(type)) {
            return null;
        }
        for (ApiParamTypeEnum typeEnum : ApiParamTypeEnum.values()) {
            if (String.valueOf(typeEnum.getType()).equals(type)) {
                return typeEnum;
            }
        }
        return null;
    }

}
