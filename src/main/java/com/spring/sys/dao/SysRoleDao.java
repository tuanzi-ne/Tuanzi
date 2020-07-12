package com.spring.sys.dao;

import com.spring.common.mvc.BaseDao;
import com.spring.sys.pojo.SysRoleInfo;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

/**
 * 系统角色Dao接口类
 *
 * @author  团子
 * @since: V1.0
 * @date 2018/3/2 13:37
 */
@Repository
public interface SysRoleDao extends BaseDao<SysRoleInfo> {

    /**
     * 是否存在用户
     */
    Integer hasRole(Map<String, Object> map);

    /**
     * 根据roleId查询资源信息
     */
    List<Integer> getResByRId(Integer roleId);

    /**
     * 删除角色资源关系
     */
    void delRoleRes(Integer roleId);

    /**
     * 保存角色资源关系
     */
    void saveRoleRes(Map<String, Object> map);
}
