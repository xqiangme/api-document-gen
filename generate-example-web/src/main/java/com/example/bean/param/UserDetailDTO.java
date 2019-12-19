
package com.example.bean.param;


import com.example.bean.base.BaseOperateModel;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 人员信息删除参数
 *
 * @author mengqiang
 */
@Data
public class UserDetailDTO extends BaseOperateModel {

    /**
     * 人员id
     */
    @NotBlank(message = "人员id不为空！")
    private String userId;
    /**
     * 人员数组
     */
    private String[] userIds;


    /**
     * Integer类型
     */
    private Integer idInteger;

    /**
     * int 类型
     */
    private int idInt;


    /**
     * boolean 类型
     */
    private boolean booleanFlag;

}
