
package com.example.bean.param;


import com.example.bean.base.BaseOperateModel;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * 人员信息修改参数
 *
 * @author 码农猿
 */
@Data
public class UserUpdateDTO extends BaseOperateModel {

    /**
     * 人员id
     */
    @NotBlank(message = "人员id不为空！")
    private String userId;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不为空！")
    @Max(value = 128)
    private String userName;

    /**
     * 密码
     */
    @NotBlank(message = "密码不为空！")
    @Max(value = 64)
    private String userPassword;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别类型
     * 1-男，2-女，3-不详
     */
    @NotNull
    private Integer sexType;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不为空！")
    @Max(value = 11)
    private String mobile;

    /**
     * remark测试
     */
    private String remark;

}
