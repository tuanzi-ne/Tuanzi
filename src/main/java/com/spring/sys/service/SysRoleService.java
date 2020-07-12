package com.spring.sys.service;

import com.spring.sys.pojo.SysRoleInfo;

import java.util.List;
import java.util.Map;

/**
 * 角色管理
 *
 * @author  团子
 * @date 2018/3/16 14:58
 * @since V1.0
 */
public interface SysRoleService {

    /**
     * 查询角色列表
     */
    List<SysRoleInfo> queryList(Map<String, Object> map);

    /**
     * 验证是否存在角色
     */
    boolean hasRole(Map<String, Object> map);

    /**
     * 根据roleId查询角色信息
     */
    SysRoleInfo getRoleByRId(Integer roleId);

    /**
     * 根据roleId查询资源信息
     */
    List<Integer> getResByRId(Integer roleId);

    /**
     * 保存角色新增
     */
    void save(SysRoleInfo role);

    /**
     * 保存角色修改
     */
    void update(SysRoleInfo role);

    /**
     * 删除角色
     */
    void delete(Integer[] roleIds);


    void savePerms(SysRoleInfo role);
}
