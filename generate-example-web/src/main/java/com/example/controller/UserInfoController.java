
package com.example.controller;

import com.example.bean.base.BasePageModelResult;
import com.example.bean.param.*;
import com.example.bean.result.Response;
import com.example.bean.result.UserDetailManyResult;
import com.example.bean.result.UserDetailResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * 人员管理接口
 *
 * @author mengqiang
 * @version UserInfoController.java
 */
@RestController
@RequestMapping("/user")
public class UserInfoController {


    /**
     * 添加人员
     *
     * @author
     */
    @RequestMapping(value = "/add-user", method = {RequestMethod.DELETE, RequestMethod.POST})
    public Response addUser1(@Valid @RequestBody UserAddDTO userAddDTO) {
        return null;
    }

    /**
     * 删除人员
     *
     * @author mengqiang
     */
    @DeleteMapping("/delete-user")
    public Response deleteUser1(@Valid @RequestBody UserDeleteDTO deleteDTO) {
        return null;
    }


    /**
     * 修改人员
     *
     * @author mengqiang
     */
    @PutMapping("/update-user")
    public Response updateUser1(@Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        return null;
    }



    /**
     * 人员-分页查询
     *
     * @author mengqiang
     */
    @PostMapping(value = "/list-page")
    public Response<BasePageModelResult<UserDetailResult>> listPage1(@Valid @RequestBody UserPageQueryDTO pageQueryDTO) {
        return null;
    }

    /**
     * 多层级参数
     *
     * @author mengqiang
     */
    @PostMapping("/add-user-many")
    public Response addUser2(@Valid @RequestBody UserAddManyDTO addManyDTO) {
        return null;
    }


    /**
     * 多层级出参
     *
     * @author mengqiang
     */
    @GetMapping("/get-user")
    public Response<UserDetailManyResult> getUser2(@RequestParam("userId") String userId) {
        return null;
    }


    /**
     * 添加人员(测试参数无注释)
     *
     * @author mengqiang
     */
    @PostMapping("/add-user2")
    public void addUser3(@Valid @RequestBody UserAddDTO2 userAddDTO) {

    }

}