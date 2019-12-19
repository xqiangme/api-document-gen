package com.tool.api.generate.core.model;

import com.tool.api.generate.core.config.custom.ApiHeaderField;
import com.tool.api.generate.core.enums.ApiParamTypeEnum;
import com.tool.api.generate.core.model.base.ApiBaseModel;
import lombok.Data;

import java.util.List;

/**
 * 接口文档批量输出 model
 *
 * @author mengqiang
 */
@Data
public class ApiWriteModel extends ApiBaseModel {

    /**
     * 是否输出json示例
     */
    private Boolean generateJsonFlag;

    /**
     * 是否输出接口方法路径
     */
    private Boolean interfacePathShowFlag;

    /**
     * 参数类型
     *
     * @see ApiParamTypeEnum
     */
    private Integer paramType;

    /**
     * 环境级别
     * DEBUG 情况下 为1 ，输出层级
     */
    private Integer logLevel;

    /**
     * content - type
     */
    private String contentType;

    /**
     * 头信息-属性集
     */
    private List<ApiHeaderField> headerFieldList;

    /**
     * 多个controller,接口批量生成
     */
    private List<ApiOutModel> apiWriteList;


}