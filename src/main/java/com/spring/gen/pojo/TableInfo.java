package com.spring.gen.pojo;

import java.util.Date;
import java.util.List;

/**
 * 数据库表Pojo类
 * @author  团子
 * @since V1.0
 * @date 2018/4/12 16:50
 */
public class TableInfo {
	/**
	 * 表名称
	 */
	private String tableName;
    /**
     * 表备注
     */
	private String tableComment;
    /**
     * 表引擎
     */
	private String engine;
    /**
     * 主键列
     */
	private ColumnInfo pk;
    /**
     * 表列(不含主键)
     */
	private List<ColumnInfo> columns;
    /**
     * 首字母大写类名
     */
	private String className;
    /**
     * 首字母小写类名
     */
	private String classname;

    /**
     * 创建时间
     */
    private Date createTime;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public ColumnInfo getPk() {
        return pk;
    }

    public void setPk(ColumnInfo pk) {
        this.pk = pk;
    }

    public List<ColumnInfo> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnInfo> columns) {
        this.columns = columns;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
