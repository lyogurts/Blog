package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.entity.Menu;
import com.sangeng.mapper.MenuMapper;
import com.sangeng.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(SysMenu)表服务实现类
 *
 * @author makejava
 * @since 2023-03-24 10:38:18
 */
@Service("MenuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {


    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是系统管理员，返回所有的权限
        if (id==1L){
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
             queryWrapper.in(Menu::getMenuType, SystemConstants.MENU,SystemConstants.BUTTON);
             queryWrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> list = list(queryWrapper);
            //权限集合
            List<String> perms = list.stream()
            .map(Menu::getPerms).
            collect(Collectors.toList());
            return perms;
        }


        //返回用户具有的权限信息


        return getBaseMapper().selectPermsByUserId(id);
    }
}

