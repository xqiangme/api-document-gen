package com.tool.api.generate.core.model;


import com.tool.api.generate.core.model.base.ApiBaseModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 参数属性model
 *
 * @author mengqiang
 */
@Data
public class ParamModel extends ApiBaseModel {

    /**
     * 参数名
     */
    private String name;

    /**
     * 参数描述
     */
    private String description;

    /**
     * 参数类型
     */
    private String type;

    /**
     * 是否必填， 1 -是 2- 否
     */
    private Integer requiredFlag = 1;

    /**
     * 长度
     */
    private String length;

    /**
     * 子参数总长度
     */
    private Integer childParamSize = 0;

    /**
     * 拥有子参数的参数数量
     */
    private Integer hasChildParamSize = 0;

    /**
     * 子参数
     */
    private List<ParamModel> children = new ArrayList<>();

    public ParamModel() {
    }

    public ParamModel(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public ParamModel(String name, String type, Integer requiredFlag) {
        this.name = name;
        this.type = type;
        this.requiredFlag = requiredFlag;
    }

    public ParamModel(String name, String description, String type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }
}