
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
public class UserDeleteDTO extends BaseOperateModel {

    /**
     * 人员id
     */
    @NotBlank(message = "人员id不为空！")
    private String userId;

}
