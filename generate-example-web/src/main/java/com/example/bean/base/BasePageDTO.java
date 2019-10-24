
package com.example.bean.base;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;

/**
 * 分页统一参数基础抽象类
 *
 * @author 码农猿
 */
public class BasePageDTO extends BaseDTO {

    private static final long serialVersionUID = -1L;


    @NotNull(message = "当前页不为空！")
    private Integer page;

    @NotNull(message = "页容量不为空！")
    private Integer pageSize;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}