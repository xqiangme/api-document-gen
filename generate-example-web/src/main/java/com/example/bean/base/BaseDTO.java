package com.example.bean.base;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 统一参数基础抽象类
 *
 * @author mengqiang
 */
public class BaseDTO implements Serializable {

    private static final long serialVersionUID = -1L;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}