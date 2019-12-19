
package com.example.bean.param;

import com.example.bean.base.BaseDTO;
import lombok.Data;

import java.util.List;

/**
 * 角色参数
 *
 * @author mengqiang
 * @version RoleDTO.java, v 0.1
 */
@Data
public class RoleDTO extends BaseDTO {

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 角色名称
     */
    private String roleName;


    /**
     * 菜单集合
     */
    private List<MenuInfoDTO> menuList;
}