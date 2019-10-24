package com.my.api.generate.core.model;


import com.my.api.generate.core.model.base.ApiBaseModel;
import lombok.Data;

import java.util.List;

/**
 * 接口请求-入参 model
 *
 * @author mengqiang
 */
@Data
public class ApiReqParamModel extends ApiBaseModel {

    /**
     * 参数集
     */
    private List<ParamModel> paramList;

    /**
     * 参数总数量
     */
    private Integer paramTotalSize=0;

    /**
     * 参数总层级
     */
    private Integer paramTotalLevel=0;

    /**
     * 参数示例 json字符串集
     */
    private List<String> jsonExampleList;

    public ApiReqParamModel() {
    }

    public ApiReqParamModel(List<ParamModel> paramList, Integer paramTotalLevel, List<String> jsonExampleList) {
        this.paramList = paramList;
        this.paramTotalLevel = paramTotalLevel;
        this.jsonExampleList = jsonExampleList;
    }

    public ApiReqParamModel(List<ParamModel> paramList, Integer paramTotalSize, Integer paramTotalLevel, List<String> jsonExampleList) {
        this.paramList = paramList;
        this.paramTotalSize = paramTotalSize;
        this.paramTotalLevel = paramTotalLevel;
        this.jsonExampleList = jsonExampleList;
    }
}