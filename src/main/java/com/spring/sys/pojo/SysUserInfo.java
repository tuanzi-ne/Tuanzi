package com.spring.sys.pojo;


import com.spring.common.validator.group.AddGroup;
import com.spring.common.validator.group.UpdateGroup;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户pojo类
 *
 * @author  团子
 * @since: V1.0
 * @date 2018/3/2 13:14
 */
public class SysUserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空!", groups = {AddGroup.class, UpdateGroup.class})
    private String username;
    /**
     * 用户真实姓名
     */
    private String realname;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空!", groups = {AddGroup.class})
    private String password;

    /**
     * 加盐
     */
    private String salt;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空!", groups = {AddGroup.class, UpdateGroup.class})
    @Email(message = "邮箱格式不正确!", groups = {AddGroup.class, UpdateGroup.class})
    private String email;

    /**
     * 状态,1表示正常; 0表示禁用
     */
    @NotNull(message = "状态不能为空!", groups = {AddGroup.class, UpdateGroup.class})
    private Integer status;

    /**
     * 部门ID
     */
    private Integer deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 角色ID列表
     */
    private List<Integer> roleIds = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDeptName() { return deptName; }

    public void setDeptName(String deptName) { this.deptName = deptName; }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }
}
