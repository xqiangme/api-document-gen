
package com.example.bean.base;

import java.util.List;

/**
 * 分页返回基础类
 *
 * @author 码农猿
 */
public class ListItemResponse<T> extends BasePageModel {

    private static final long serialVersionUID = -1L;

    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
