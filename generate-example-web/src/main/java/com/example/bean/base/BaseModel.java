package com.example.bean.base;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 基础 model
 *
 * @author 码农猿
 * @version BaseResult.java, v 3.0 2019-01-07 22:17
 */
@Data
public class BaseModel implements Serializable {

    private static final long serialVersionUID = -1L;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }


}