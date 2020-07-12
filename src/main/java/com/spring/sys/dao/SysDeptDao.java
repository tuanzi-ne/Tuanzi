package com.spring.sys.dao;

import com.spring.common.mvc.BaseDao;
import com.spring.sys.pojo.SysRoleInfo;
import com.spring.sys.pojo.SysDeptInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 系统部门管理Dao接口
 *
 * @author  团子
 * @since: V1.0
 * @date 2018/3/2 13:37
 */
@Repository
public interface SysDeptDao extends BaseDao<SysDeptInfo> {

    /**
     * 是否存在部门
     */
    Integer hasDept(Map<String, Object> map);
}
