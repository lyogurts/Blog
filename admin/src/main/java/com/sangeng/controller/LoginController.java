package com.sangeng.controller;

import com.sangeng.domain.entity.AdminUserInfoVo;
import com.sangeng.domain.entity.LoginUser;
import com.sangeng.domain.entity.ResponseResult;
import com.sangeng.domain.entity.User;
import com.sangeng.domain.vo.UserInfoVo;
import com.sangeng.enums.AppHttpCodeEnum;
import com.sangeng.exception.SystemException;
import com.sangeng.service.LoginService;
import com.sangeng.service.MenuService;
import com.sangeng.service.RoleService;
import com.sangeng.utils.BeanCopyUtils;
import com.sangeng.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }
    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){

        //获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询信息
        Long userId = SecurityUtils.getUserId();
        List<String> perms = menuService.selectPermsByUserId(userId);
        //根据用户id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId())   ;
        //获取用户数据
        User user = loginUser.getUser();
        //BeanCopy
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roleKeyList, userInfoVo);
        return   ResponseResult.okResult(adminUserInfoVo);
    }

}