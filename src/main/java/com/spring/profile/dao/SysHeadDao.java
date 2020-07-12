package com.spring.profile.dao;

import com.spring.common.mvc.BaseDao;
import com.spring.profile.pojo.SysHeadInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Title: SysHeadDao
 * @Description: 系统头像
 * @author  团子
 * @date 2018/9/2 9:19
 * @since v1.0
 */
@Repository
public interface SysHeadDao extends BaseDao {
    /**
     * 根据id查询头像
     * @param userId
     * @return 头像信息
     */
    SysHeadInfo queryHeadInfoById(@Param("userId") Integer userId);
}
