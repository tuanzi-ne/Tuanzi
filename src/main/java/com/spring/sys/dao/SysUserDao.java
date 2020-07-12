package com.spring.sys.dao;

import com.spring.common.mvc.BaseDao;
import com.spring.sys.pojo.SysRoleInfo;
import com.spring.sys.pojo.SysUserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 系统用户管理Dao接口
 *
 * @author  团子
 * @since: V1.0
 * @date 2018/3/2 13:37
 */
@Repository
public interface SysUserDao extends BaseDao<SysUserInfo> {
    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     */
    SysUserInfo queryByUserName(String username);

    /**
     * 根据用户ID查询用户所有权限
     *
     * @param userId 用户ID
     */
    List<String> queryAllPerms(Integer userId);

    /**
     * 根据用户ID获取角色列表
     */
    List<SysRoleInfo> queryRolesByUId(@Param("userId") Integer userId);

    /**
     * 删除用户角色关系
     */
    void delUserRole(Integer userId);

    /**
     * 保存用户角色关系
     */
    void saveUserRole(Map<String, Object> map);

    /**
     * 是否存在用户
     */
    Integer isHasUser(Map<String, Object> map);

    /**
     * 重置密码
     */
    void resetPwd(List<SysUserInfo> userList);

    /**
     * 是否存在密码
     */
    Integer hasPwd(Map<String, Object> map);

    /**
     * 更新密码
     */
    void updatePwd(Map<String, Object> map);

}
