package com.my.api.generate.core.model.base;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 基础 model
 *
 * @author mengqiang
 */
@Data
public class ApiBaseModel implements Serializable {

    private static final long serialVersionUID = -1L;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}