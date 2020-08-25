package com.spring.sys.service;


import com.spring.sys.pojo.SysRoleInfo;
import com.spring.sys.pojo.SysUserInfo;
import java.util.List;
import java.util.Map;


/**
 * 系统用户管理service接口
 *
 * @author  团子
 * @date 2018/3/2 13:13
 * @since V1.0
 */
public interface SysUserService {

    /**
     * 查询用户列表
     */
    List<SysUserInfo> queryList(Map<String, Object> map);
    /**
     * 验证是否存在用户
     */
    boolean isHasUser(Map<String, Object> map);
    /**
     * 保存用户
     */
    void save(SysUserInfo user);
    /**
     * 根据用户ID获取角色列表
     */
    List<SysRoleInfo> queryRolesByUId(Integer userId);
    /**
     * 根据userId获取用户信息
     */
    SysUserInfo getUserByUId(Integer userId);
    /**
     * 保存用户修改
     */
    void update(SysUserInfo user);
    /**
     * 删除用户
     */
    void delete(Integer[] userIds);
    /**
     * 重置密码
     */
    void resetPwd(Integer[] userIds);

    /**
     * 是否存在密码
     */
    boolean hasPwd(Map<String, Object> map);
    /**
     * 更新密码
     */
    void updatePwd(Integer userId, String password);

    /**
     * 根据参数key得到参数value
     */
    String getParamValueByKey(String key);

    /**
     * 根据参数key得到参数value,不存在返回默认值
     */
    String getParamValueByKey(String key, String defaultValue);

    SysUserInfo registered(SysUserInfo user) throws Exception;
}
