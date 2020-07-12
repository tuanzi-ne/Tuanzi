package com.spring.sys.service;

import com.spring.sys.pojo.SysResInfo;
import java.util.List;
import java.util.Map;


/**
 * 系统资源管理service接口
 *
 * @author  团子
 * @date 2018/3/2 13:13
 * @since V1.0
 */
public interface SysResService {

    /**
     * 查询资源列表
     */
    List<SysResInfo> queryList(Map<String, Object> map);

    /**
     * 获取用户拥有资源
     */
    List<SysResInfo> getNav(Integer userId);

    /**
     * 验证是否存在资源
     */
    boolean hasRes(Map<String, Object> map);

    /**
     * 保存资源
     */
    void save(SysResInfo res);

    /**
     * 根据resId获取资源信息
     */
    SysResInfo getResByUId(Integer resId);

    /**
     * 保存资源修改
     */
    void update(SysResInfo res);

    /**
     * 删除资源
     */
    void delete(Integer[] ids);

    /**
     * 选择资源
     */
    List<SysResInfo> selectResById(String resId);
}
