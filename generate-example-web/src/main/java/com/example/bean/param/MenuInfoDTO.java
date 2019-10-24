
package com.example.bean.param;

import com.example.bean.base.BaseDTO;
import lombok.Data;

/**
 * 菜单参数
 *
 * @author 码农猿
 * @version MenuInfoDTO.java, v 0.1
 */
@Data
public class MenuInfoDTO extends BaseDTO {

    /**
     * 菜单id
     */
    private String menuId;

    /**
     * 菜单名称
     */
    private String menuName;
}