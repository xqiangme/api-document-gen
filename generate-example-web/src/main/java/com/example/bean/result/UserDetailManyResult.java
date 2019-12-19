package com.example.bean.result;


import com.example.bean.base.BaseModel;
import com.example.bean.param.MenuInfoDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 人员信息详情返回参数
 *
 * @author mengqiang
 */
@Data
public class UserDetailManyResult extends BaseModel {

    /**
     * 自增 id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别类型
     * 1-男，2-女，3-不详
     */
    private Integer sexType;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * remark测试
     */
    private String remark;

    /**
     * 创建时间戳
     */
    private Date createTime;

    /**
     * 更新时间戳
     */
    private Date updateTime;

    /**
     * 菜单集
     */
    private List<MenuInfoDTO> menuList;
}
