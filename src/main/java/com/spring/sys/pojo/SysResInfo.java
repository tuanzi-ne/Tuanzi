package com.spring.sys.pojo;

import com.spring.common.validator.group.AddGroup;
import com.spring.common.validator.group.UpdateGroup;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 资源pojo类
 *
 * @author  团子
 * @since: V1.0
 * @date 2018/3/2 13:14
 */
public class SysResInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 资源ID
     */
    private Integer id;

    /**
     * 资源名称
     */
    @NotBlank(message = "资源名称不能为空!", groups = {AddGroup.class, UpdateGroup.class})
    private String resName;

    /**
     * 资源描述
     */
    private String resDesc;

    /**
     * 资源类型 0：目录   1：菜单   2：按钮
     */
    @NotNull(message = "资源类型不能为空!", groups = {AddGroup.class, UpdateGroup.class})
    private Integer resType;

    /**
     * 资源URL
     */
    private String resUrl;

    /**
     * 资源权限标识
     */
    private String resPerms;

    /**
     * 资源图标
     */
    private String resIcon;

    /**
     * 上级资源ID
     */
    @NotNull(message = "上级资源不能为空!", groups = {AddGroup.class, UpdateGroup.class})
    private Integer parentId;

    /**
     * 上级资源名称
     */
    private String parentName;

    /**
     * 排序号
     */
    private String orderNo;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * ztree属性，是否展开（true：展开,false：不展开）
     */
    private Boolean open;

    private List<?> list;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName == null ? "" : resName.trim();
    }

    public String getResDesc() {
        return resDesc;
    }

    public void setResDesc(String resDesc) {
        this.resDesc = resDesc == null ? "" : resDesc.trim();
    }

    public Integer getResType() {
        return resType;
    }

    public void setResType(Integer resType) {
        this.resType = resType;
    }

    public String getResUrl() {
        return resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl == null ? "" : resUrl.trim();
    }

    public String getResPerms() {
        return resPerms;
    }

    public void setResPerms(String resPerms) {
        this.resPerms = resPerms == null ? "" : resPerms.trim();
    }

    public String getResIcon() {
        return resIcon;
    }

    public void setResIcon(String resIcon) {
        this.resIcon = resIcon == null ? "" : resIcon.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }
}
