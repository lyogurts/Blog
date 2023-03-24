package com.sangeng.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sangeng.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(SysMenu)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-24 10:38:14
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * @param userId
     * @return
     */
    List<String> selectPermsByUserId(Long userId);
}

