package com.spring.sys.dao;

import com.spring.common.mvc.BaseDao;
import com.spring.sys.pojo.SysParamInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 系统参数
 *
 * @author  团子
 * @date 2018-08-26 08:14:13
 * @since V1.0
 */
@Repository
public interface SysParamDao extends BaseDao<SysParamInfo> {

    /**
     * 验证是否存在参数
     */
    Integer isHasKey(Map<String, Object> map);
    /**
     * 查询所有参数
     */
    List<SysParamInfo> queryAllList();
}
