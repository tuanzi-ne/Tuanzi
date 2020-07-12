package com.spring.sys.pojo;

import java.io.Serializable;
import java.util.Date;


/**
 * 系统异常Pojo类
 *
 * @author  团子
 * @date 2018/4/10 17:39
 * @since V1.0
 */
public class SysExpInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 日志ID
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 异常信息
     */
    private String expection;
    /**
     * 创建时间
     */
    private Date createTime;

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

    public String getExpection() {
        return expection;
    }

    public void setExpection(String expection) {
        this.expection = expection;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
