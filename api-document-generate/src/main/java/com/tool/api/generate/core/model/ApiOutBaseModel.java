package com.tool.api.generate.core.model;

import com.tool.api.generate.core.model.base.ApiBaseModel;
import lombok.Data;

/**
 * 接口文档输出 model
 *
 * @author mengqiang
 */
@Data
public class ApiOutBaseModel extends ApiBaseModel {

    /**
     * 接口标题
     * 注：默认取方法注释，注释不存在情况下取方法名
     */
    private String apiTitle;

    /**
     * 作者
     */
    private String apiAuthor;

    /**
     * 访问地址
     */
    private String reqPath;

    /**
     * 请求类型
     * 示例： POST..
     */
    private String reqType;

    /**
     * 接口类名
     * 示例：com.demo.UserService
     */
    private String interfaceClassName;

    /**
     * 接口方法名
     * 示例：add
     */
    private String interfaceMethodName;

    /**
     * 网关api 请求方法名
     * 示例：com.my.demo.addUser
     */
    private String gateWayApiMethod;

    /**
     * 请求参数 model
     */
    private ApiReqParamModel reqParam;

    /**
     * 返回参数 model
     */
    private ApiResParamModel resParam;


}