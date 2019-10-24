
package com.example.bean.base;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 基础 model 带有操作人信息
 *
 * @author 码农猿
 */
@Data
public class BaseOperateModel implements Serializable {

    private static final long serialVersionUID = -1L;


    private String operateBy;
    /**
     * 操作人名称
     */
    private String operateName;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }


}