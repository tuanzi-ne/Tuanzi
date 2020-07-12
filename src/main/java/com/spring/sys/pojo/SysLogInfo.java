package com.spring.sys.pojo;

import java.io.Serializable;
import java.util.Date;


/**
 * 系统日志Pojo类
 *
 * @author  团子
 * @date 2018/4/10 17:39
 * @since V1.0
 */
public class SysLogInfo implements Serializable {
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
     * 日志描述
     */
    private String desc;
    /**
     * IP地址
     */
    private String ip;
    /**
     * 请求方式
     */
    private String method;
    /**
     * 请求Url
     */
    private String url;
    /**
     * 请求参数
     */
    private String param;
    /**
     * 执行时长(毫秒)
     */
    private Long runtime;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Long getRuntime() {
        return runtime;
    }

    public void setRuntime(Long runtime) {
        this.runtime = runtime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
