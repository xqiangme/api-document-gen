
package com.example.bean.param;


import com.example.bean.base.BaseOperateModel;
import lombok.Data;

/**
 * 人员信息新增参数
 *
 * @author mengqiang
 */
@Data
public class UserAddDTO2 extends BaseOperateModel {

    /**
     * 用户名
     */
    private String userName;


    private String userPassword;


    private String realName;

    private Integer sexType;

    private String mobile;

    private String remark;


}
