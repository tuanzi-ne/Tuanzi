package com.spring.sys.service.impl;

import com.spring.common.mvc.AbstractLogging;
import com.spring.common.shiro.ShiroUtils;
import com.spring.common.utils.Constants;
import com.spring.sys.dao.SysUserDao;
import com.spring.sys.pojo.SysParamInfo;
import com.spring.sys.pojo.SysRoleInfo;
import com.spring.sys.pojo.SysUserInfo;
import com.spring.sys.service.SysParamService;
import com.spring.sys.service.SysUserService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 系统用户管理service接口实现类
 *
 * @author  团子
 * @since: V1.0
 * @date 2018/3/2 13:37
 */
@Service("sysUserService")
public class SysUserServiceImpl extends AbstractLogging implements SysUserService {
    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysParamService sysParamService;

    /**
     * 查询用户列表
     */
    @Override
    public List<SysUserInfo> queryList(Map<String, Object> map) {
        return sysUserDao.queryList(map);
    }

    /**
     * 验证是否存在用户
     */
    @Override
    public boolean isHasUser(Map<String, Object> map) {
        return sysUserDao.isHasUser(map) > 0;
    }

    /**
     * 保存用户
     */
    @Override
    @Transactional(rollbackFor = Exception.class, value = "transactionManager")
    public void save(SysUserInfo user) {
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        String salt = RandomStringUtils.randomAlphanumeric(20);
        //sha256加密
        user.setPassword(ShiroUtils.sha256(user.getPassword(), salt));
        user.setSalt(salt);
        sysUserDao.save(user);
        //保存用户角色关系
        saveOrUpdate(user.getId(), user.getRoleIds());
    }

    /**
     * 保存用户角色关系
     */
    private void saveOrUpdate(Integer userId, List<Integer> roleIds) {
        if (roleIds.size() == 0) {
            return;
        }
        //先删除用户与角色关系
        log.info("=====删除用户{}与角色关系,开始时间:{}=====", userId, new DateTime().toString("yyyy-MM-dd HH:mm:ss.SSS"));
        sysUserDao.delUserRole(userId);
        log.info("=====删除用户{}与角色关系,结束时间:{}=====", userId, new DateTime().toString("yyyy-MM-dd HH:mm:ss.SSS"));
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("roleIds", roleIds);
        //保存用户与角色关系
        log.info("=====保存用户{}与角色关系,开始时间:{}=====", userId, new DateTime().toString("yyyy-MM-dd HH:mm:ss.SSS"));
        sysUserDao.saveUserRole(map);
        log.info("=====保存用户{}与角色关系,结束时间:{}=====", userId, new DateTime().toString("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    /**
     * 根据userId获取用户角色
     */
    @Override
    public List<SysRoleInfo> queryRolesByUId(Integer userId) {
        return sysUserDao.queryRolesByUId(userId);
    }

    /**
     * 根据userId获取用户信息
     */
    @Override
    public SysUserInfo getUserByUId(Integer userId) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("userId", userId);
        List<SysUserInfo> userList = sysUserDao.queryList(map);
        SysUserInfo uinfo = userList.get(0);
        List<SysRoleInfo> roles = sysUserDao.queryRolesByUId(userId);
        for (SysRoleInfo info : roles) {
            uinfo.getRoleIds().add(info.getId());
        }
        return uinfo;
    }

    /**
     * 保存用户修改
     */
    @Override
    @Transactional(rollbackFor = Exception.class, value = "transactionManager")
    public void update(SysUserInfo user) {
        user.setUpdateTime(new Date());
        sysUserDao.update(user);
        List<Integer> roleIds = user.getRoleIds();
        if (roleIds != null && roleIds.size() > 0) {
            //保存用户角色关系
            saveOrUpdate(user.getId(), roleIds);
        }
    }

    /**
     * 删除用户
     */
    @Override
    @Transactional(rollbackFor = Exception.class, value = "transactionManager")
    public void delete(Integer[] userIds) {
        //保存用户与角色关系
        log.info("=====删除用户{},开始时间:{}=====", StringUtils.join(userIds, ","), new DateTime().toString("yyyy-MM-dd HH:mm:ss.SSS"));
        sysUserDao.delete(userIds);
        log.info("=====删除用户{},结束时间:{}=====", StringUtils.join(userIds, ","), new DateTime().toString("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    /**
     * 重置密码
     */
    @Override
    @Transactional(rollbackFor = Exception.class, value = "transactionManager")
    public void resetPwd(Integer[] userIds) {
        List<SysUserInfo> userList = sysUserDao.queryListBatch(userIds);
        for (SysUserInfo user : userList) {
            user.setPassword(ShiroUtils.sha256(getParamValueByKey("user.reset.pwd", Constants.USER_RESET_PWD), user.getSalt()));
            user.setUpdateTime(new Date());
        }
        sysUserDao.resetPwd(userList);
    }


    /**
     * 是否存在密码
     */
    @Override
    public boolean hasPwd(Map<String, Object> map) {
        String password = ShiroUtils.sha256(String.valueOf(map.get("oldPwd")), ShiroUtils.getUserInfo().getSalt());
        map.put("password",password);
        map.put("username",ShiroUtils.getUserInfo().getUsername());
        return sysUserDao.hasPwd(map) > 0;
    }

    /**
     * 更新密码
     */
    @Override
    public  void updatePwd(Integer userId,String password){
        Map<String, Object> map = new HashMap();
        map.put("password",password);
        map.put("userId",userId);
        map.put("updateTime",new Date());
        sysUserDao.updatePwd(map);
    }

    /**
     * 查询获取所有key/value对并缓存到Ehcache
     */
    private Map<String, String> getAllParams() {
        List<SysParamInfo> paramList = sysParamService.queryAllList();
        Map<String, String> paramMap = new HashMap<>(100);
        for (SysParamInfo info : paramList) {
            paramMap.put(info.getParamKey(), info.getParamValue());
        }
        return paramMap;
    }

    /**
     * 根据参数key得到参数value
     */
    @Override
    public String getParamValueByKey(String key) {
        return getParamValueByKey(key, "");
    }

    /**
     * 根据参数key得到参数value,不存在返回默认值
     */
    @Override
    public String getParamValueByKey(String key, String defaultValue) {
        String value = getAllParams().get(key);
        return value == null || value.isEmpty() ? defaultValue : value.trim();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, value = "transactionManager")
    public SysUserInfo registered(SysUserInfo user) throws Exception {


        SysUserInfo userInfo = sysUserDao.queryByUserName(user.getUsername());

        if (userInfo != null){

            throw new Exception("用户名已存在");
        }

        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        String salt = RandomStringUtils.randomAlphanumeric(20);
        //sha256加密
        user.setPassword(ShiroUtils.sha256(user.getPassword(), salt));
        user.setSalt(salt);
        sysUserDao.save(user);

        List<Integer> roleIds = new ArrayList<>();

        roleIds.add(2);

        user.setRoleIds(roleIds);
        //保存用户角色关系
        saveOrUpdate(user.getId(), user.getRoleIds());

        return user;
    }
}
