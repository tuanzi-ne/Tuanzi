package com.spring.sys.service.impl;

import com.spring.common.mvc.AbstractLogging;
import com.spring.sys.dao.SysRoleDao;
import com.spring.sys.pojo.SysRoleInfo;
import com.spring.sys.service.SysRoleService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统角色service接口实现类
 *
 * @author  团子
 * @since: V1.0
 * @date 2018/3/2 13:37
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends AbstractLogging implements SysRoleService {

    @Autowired
    private SysRoleDao sysRoleDao;

    /**
     * 查询角色列表
     */
    @Override
    public List<SysRoleInfo> queryList(Map<String, Object> map) {
        return sysRoleDao.queryList(map);
    }

    /**
     * 根据roleId查询资源信息
     */
    @Override
    public List<Integer> getResByRId(Integer roleId) {
        return sysRoleDao.getResByRId(roleId);
    }

    /**
     * 验证是否存在角色
     */
    @Override
    public boolean hasRole(Map<String, Object> map) {
        return sysRoleDao.hasRole(map) > 0;
    }


    /**
     * 根据roleId获取资源信息
     */
    @Override
    public SysRoleInfo getRoleByRId(Integer roleId) {
        Map<String, Object> map = new HashMap<>();
        map.put("roleId", roleId);
        List<SysRoleInfo> resList = sysRoleDao.queryList(map);
        return resList.get(0);
    }

    /**
     * 保存角色新增
     */
    @Override
    @Transactional(rollbackFor = Exception.class, value = "transactionManager")
    public void save(SysRoleInfo role) {
        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());
        sysRoleDao.save(role);
    }


    /**
     * 保存角色修改
     */
    @Override
    @Transactional(rollbackFor = Exception.class, value = "transactionManager")
    public void update(SysRoleInfo role) {
        role.setUpdateTime(new Date());
        sysRoleDao.update(role);
    }

    /**
     * 删除角色
     */
    @Override
    @Transactional(rollbackFor = Exception.class, value = "transactionManager")
    public void delete(Integer[] roleIds) {
        log.info("=====删除角色{},开始时间:{}=====", StringUtils.join(roleIds, ","), new DateTime().toString("yyyy-MM-dd HH:mm:ss.SSS"));
        sysRoleDao.delete(roleIds);
        log.info("=====删除角色{},结束时间:{}=====", StringUtils.join(roleIds, ","), new DateTime().toString("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, value = "transactionManager")
    public void savePerms(SysRoleInfo role) {
        if (ArrayUtils.isEmpty(role.getList())) {
            return;
        }
        //先删除角色与资源关系
        log.info("=====删除角色{}与资源关系,开始时间:{}=====", role.getId(), new DateTime().toString("yyyy-MM-dd HH:mm:ss.SSS"));
        sysRoleDao.delRoleRes(role.getId());
        log.info("=====删除角色{}与资源关系,结束时间:{}=====", role.getId(), new DateTime().toString("yyyy-MM-dd HH:mm:ss.SSS"));
        Map<String, Object> map = new HashMap<>();
        map.put("roleId", role.getId());
        map.put("resIds", role.getList());
        //保存角色与资源关系
        log.info("=====保存用户{}与角色关系,开始时间:{}=====", role.getId(), new DateTime().toString("yyyy-MM-dd HH:mm:ss.SSS"));
        sysRoleDao.saveRoleRes(map);
        log.info("=====保存用户{}与角色关系,结束时间:{}=====", role.getId(), new DateTime().toString("yyyy-MM-dd HH:mm:ss.SSS"));
    }

}
