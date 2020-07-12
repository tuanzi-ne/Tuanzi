package com.spring.sys.service;

import com.spring.sys.pojo.SysDeptInfo;

import java.util.List;
import java.util.Map;


/**
 * 系统部门管理service接口
 *
 * @author  团子
 * @date 2018/3/2 13:13
 * @since V1.0
 */
public interface SysDeptService {

    /**
     * 查询部门列表
     */
    List<SysDeptInfo> queryList(Map<String, Object> map);


    /**
     * 验证是否存在部门
     */
    boolean hasDept(Map<String, Object> map);

    /**
     * 保存部门
     */
    void save(SysDeptInfo dept);

    /**
     * 根据deptId获取部门信息
     */
    SysDeptInfo getDeptById(Integer deptId);

    /**
     * 根据deptId选择部门
     */
    List<SysDeptInfo> selectDeptById(String deptId);

    /**
     * 保存部门修改
     */
    void update(SysDeptInfo dept);

    /**
     * 删除部门
     */
    void delete(Integer[] deptIds);
}
