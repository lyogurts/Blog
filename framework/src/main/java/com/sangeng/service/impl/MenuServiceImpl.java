package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.entity.Menu;
import com.sangeng.mapper.MenuMapper;
import com.sangeng.service.MenuService;
import com.sangeng.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 菜单权限表(SysMenu)表服务实现类
 *
 * @author makejava
 * @since 2023-03-24 10:38:18
 */
@Service("MenuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

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

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        List<Menu> menus =null;
        //判断是否是管理员
        if (SecurityUtils.isAdmin()){
            //如果是  获取所有符合要求的Menu
          menus=   menuMapper.selectAllRouterMenu();
        }else {
            //否则 获取当前用户所具有的Menu
            menus= menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        //构建tree
        //先找出第一层的菜单 然后去找他们的子菜单设置到children属性
        List<Menu> menuTree = builderMenuTree(menus,0L);
        return menuTree;
    }

    /**
     *
     * @param menus 当前用户所具有的Menu集合
     * @param parentId parentId为0
     * @return
     */
    private List<Menu> builderMenuTree(List<Menu> menus, long parentId) {
        List<Menu> menuTree = menus.stream()
                //parentId为0只剩下第一个menu
                .filter(menu -> menu.getParentId().equals(parentId))
                //子知父，父不知子
                /**
                 * menu  过滤后的菜单，这里是parentId=0的菜单
                 * menus  集合 用户拥有的所有集合
                 */
                .map(menu -> menu.setChildren(getChildren(menu,menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     *
     * @param menu 父菜单
     * @param menus  当前用户所有的菜单集合
     * @return
     */
    private List<Menu> getChildren(Menu menu,List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
//        当前用户所有的菜单集合的parentId等于上面父类的id
                .filter(menu1 -> menu1.getParentId().equals(menu.getId()))
                .map(m -> m.setChildren(getChildren(m,menus)) )
                .collect(Collectors.toList());
        return childrenList;
    }
}

