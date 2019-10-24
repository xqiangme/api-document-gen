package com.my.api.generate.core.model;


import com.my.api.generate.core.model.base.ApiBaseModel;
import lombok.Data;

import java.util.List;

/**
 * 接口 请求参数 model
 *
 * @author mengqiang
 */
@Data
public class ApiResParamModel extends ApiBaseModel {

    /**
     * 参数集
     */
    private List<ParamModel> paramList;

    /**
     * 参数总数量
     */
    private Integer paramTotalSize = 0;

    /**
     * 拥有子参数的，参数数量
     */
    private Integer hasChildParamTotalSize = 0;
    /**
     * 参数总层级
     */
    private Integer paramTotalLevel = 0;

    /**
     * 参数示例 json字符串集
     */
    private String jsonExample;

}