package com.spring.sys.service;

import com.spring.sys.pojo.SysParamInfo;

import java.util.List;
import java.util.Map;

/**
 * 系统参数
 *
 * @author  团子
 * @date 2018-08-26 08:14:13
 * @since V1.0
 */
public interface SysParamService {

    /**
     * 查询
     */
    List<SysParamInfo> queryList(Map<String, Object> map);
    /**
     * 查询所有参数
     */
    List<SysParamInfo> queryAllList();

    /**
     * 根据Id获取信息
     */
    SysParamInfo querySysParamById(Integer id);

    /**
     * 验证是否存在参数
     */
    boolean isHasKey(Map<String, Object> map);

    /**
     * 保存
     */
    void save(SysParamInfo sysParam);

    /**
     * 更新
     */
    void update(SysParamInfo sysParam);

    /**
     * 根据Id批量删除
     */
    void delete(Integer[] ids);
}
