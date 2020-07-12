package com.spring.sys.pojo;

import com.spring.common.validator.group.AddGroup;
import com.spring.common.validator.group.UpdateGroup;
import javax.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统参数
 *
 * @author  团子
 * @date 2018-08-26 08:14:13
 * @since V1.0
 */
public class SysParamInfo implements Serializable {
	private static final long serialVersionUID = 1L;

    /**
     * 自动编号
     */
	private Integer id;
    /**
     * 参数键名
     */
    @NotBlank(message = "参数键名不能为空!", groups = {AddGroup.class, UpdateGroup.class})
	private String paramKey;
    /**
     * 参数键值
     */
    @NotBlank(message = "参数键值不能为空!", groups = {AddGroup.class, UpdateGroup.class})
	private String paramValue;
    /**
     * 参数描述
     */
	private String paramDesc;
    /**
     * 创建时间
     */
	private Date createTime;
    /**
     * 最后更新时间
     */
	private Date updateTime;

    /**
     * 自动编号
     */
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }
    /**
     * 参数键名
     */
    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }
    public String getParamKey() {
        return paramKey;
    }
    /**
     * 参数键值
     */
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }
    public String getParamValue() {
        return paramValue;
    }
    /**
     * 参数描述
     */
    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
    }
    public String getParamDesc() {
        return paramDesc;
    }
    /**
     * 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getCreateTime() {
        return createTime;
    }
    /**
     * 最后更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
}
