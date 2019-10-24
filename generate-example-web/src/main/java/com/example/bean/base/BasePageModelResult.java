
package com.example.bean.base;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 分页返回基础类
 *
 * @author 码农猿
 * @version BasePageModelResult.java, v 3.0 2019-01-14 19:31
 */
public class BasePageModelResult<T> extends BasePageModel {

    private static final long serialVersionUID = -1L;

    private long total;

    private List<T> list;

    /**
     * 无数据时返回参数默认
     * total > 0
     * page > 1
     * pageSize > 10
     */
    public BasePageModelResult() {
        this.total = 0;
        this.page = 1;
        this.pageSize = 10;
        this.list = Lists.newArrayList();
    }

    public BasePageModelResult(int total, List<T> list) {
        this.total = total;
        this.list = list;
        if (CollectionUtils.isEmpty(list)) {
            this.list = Lists.newArrayList();
        }
    }


    public BasePageModelResult(int page, int pageSize, long total, List<T> returnDo) {
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
        this.list = returnDo;
    }


    public static <T> BasePageModelResult<T> newBasePageResult(int total, List<T> list) {
        return new BasePageModelResult<>(total, list);
    }


    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
