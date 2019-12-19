package com.tool.api.generate.core.model.convert;


import com.alibaba.fastjson.JSONObject;
import com.tool.api.generate.core.model.ParamModel;
import com.tool.api.generate.core.model.base.ApiBaseModel;
import lombok.Data;

import java.util.List;

/**
 * 参数转换model
 *
 * @author mengqiang
 */
@Data
public class ApiFieldParamModel extends ApiBaseModel {

    /**
     * 参数集合
     */
    private List<ParamModel> paramList;

    /**
     * 参数总数量 默认0
     */
    private Integer paramTotalSize = 0;

    /**
     * 子参数总数默认0
     */
    private Integer totalHasChildParamTotalSize = 0;

    /**
     * 参数总层级
     * 默认 0
     */
    private Integer paramTotalLevel = 0;

    /**
     * 参数示例 json字符串集
     */
    private JSONObject jsonObject;

    public ApiFieldParamModel() {
    }

    public ApiFieldParamModel(List<ParamModel> paramList) {
        this.paramList = paramList;
    }

    public ApiFieldParamModel(List<ParamModel> paramList, Integer paramTotalSize, Integer paramTotalLevel, JSONObject jsonObject) {
        this.paramList = paramList;
        this.paramTotalSize = paramTotalSize;
        this.paramTotalLevel = paramTotalLevel;
        this.jsonObject = jsonObject;
    }
}