package com.spring.sys.pojo;


import com.spring.common.validator.group.AddGroup;
import com.spring.common.validator.group.UpdateGroup;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 部门pojo类
 *
 * @author  团子
 * @since: V1.0
 * @date 2018/3/2 13:14
 */
public class SysDeptInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    private Integer id;

    /**
     * 部门名称
     */
    @NotBlank(message = "部门名称不能为空!", groups = {AddGroup.class, UpdateGroup.class})
    private String deptName;

    /**
     * 部门描述
     */
    private String deptDesc;

    /**
     * 上级部门ID
     */
    @NotNull(message = "上级部门不能为空!", groups = {AddGroup.class, UpdateGroup.class})
    private Integer parentId;

    /**
     * 上级部门名称
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptDesc() {
        return deptDesc;
    }

    public void setDeptDesc(String deptDesc) {
        this.deptDesc = deptDesc;
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
}
