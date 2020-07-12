package com.spring.common.shiro;


import com.spring.common.utils.Constants;
import com.spring.sys.dao.SysResDao;
import com.spring.sys.dao.SysUserDao;
import com.spring.sys.pojo.SysResInfo;
import com.spring.sys.pojo.SysUserInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;

/**
 * 自定义realm,实现数据源的验证和授权
 *
 * @author  团子
 * @date 2018/3/5 10:21
 * @since V1.0
 */
@Component
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysResDao sysResDao;

    /**
     * 身份验证(登录调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        // 查询用户信息
        SysUserInfo user = sysUserDao.queryByUserName(token.getUsername());
        // 账号不存在
        if (user == null) {
            throw new UnknownAccountException("此账号不存在！");
        }
        return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
    }

    /**
     * 授权(验证权限)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUserInfo user = (SysUserInfo) principals.getPrimaryPrincipal();
        Integer userId = user.getId();
        List<String> permsList;
        //系统管理员
        if (Objects.equals(userId, Constants.ADMIN_ID)) {
            List<SysResInfo> resList = sysResDao.queryList(null);
            permsList = new ArrayList<>(resList.size());
            for (SysResInfo res : resList) {
                permsList.add(res.getResPerms());
            }
        } else { //普通用户
            permsList = sysUserDao.queryAllPerms(userId);
        }

        //用户权限列表
        Set<String> permsSet = new HashSet<String>();
        for (String auth : permsList) {
            if (StringUtils.isBlank(auth)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(auth.trim().split(",")));
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 自定义密码验证
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        shaCredentialsMatcher.setHashAlgorithmName(ShiroUtils.SHA256_ALGORITHM);
        shaCredentialsMatcher.setHashIterations(ShiroUtils.HASH_ITERATIONS);
        super.setCredentialsMatcher(shaCredentialsMatcher);
    }
}
