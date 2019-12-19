package com.tool.api.generate.core.model;

import com.tool.api.generate.core.model.base.ApiBaseModel;
import lombok.Data;

import java.util.List;

/**
 * 接口文档输出 model
 *
 * @author mengqiang
 */
@Data
public class ApiOutModel extends ApiBaseModel {

    /**
     * 接口类，描述标题
     * 注：默认取类注释，类注释为空情况下取，类名
     */
    private String apiRootTitle;

    /**
     * 接口集合
     */
    private List<ApiOutBaseModel> apiBaseList;


}