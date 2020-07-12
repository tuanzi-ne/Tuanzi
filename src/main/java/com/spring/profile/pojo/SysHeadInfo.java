package com.spring.profile.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @Title: SysHeadInfo
 * @Description: 头像pojo类
 * @author  团子
 * @date 2018/9/2 19:58
 * @since v1.0
 */
public class SysHeadInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 头像ID
     */
    private Integer id;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 头像上传状态
     */
    private String status;
    /**
     * 头像原图片名称
     */
    private String srcImgName;
    /**
     * 头像图片名称
     */
    private String headImgName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSrcImgName() {
        return srcImgName;
    }

    public void setSrcImgName(String srcImgName) {
        this.srcImgName = srcImgName;
    }

    public String getHeadImgName() {
        return headImgName;
    }

    public void setHeadImgName(String headImgName) {
        this.headImgName = headImgName;
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
}
