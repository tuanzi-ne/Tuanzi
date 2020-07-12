package com.spring.profile.service;

import com.spring.profile.pojo.SysHeadInfo;

/**
 * @Title: SysHeadService
 * @Description: 系统头像
 * @author  团子
 * @date 2018/9/2 9:19
 * @since v1.0
 */
public interface SysHeadService {
    /**
     * 根据id查询头像
     */
    SysHeadInfo queryHeadInfoById(Integer userId);
    /**
     * 保存头像修改
     */
    void save(SysHeadInfo head);
}
