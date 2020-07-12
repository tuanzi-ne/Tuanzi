package com.spring.gen.pojo;

/**
 * 数据库表的列Pojo类
 *
 * @author  团子
 * @date 2018/4/12 16:50
 * @since V1.0
 */
public class ColumnInfo {
    /**
     * 列名称
     */
    private String columnName;
    /**
     * 列数据类型
     */
    private String dataType;
    /**
     * 字符串类型列长度
     */
    private String columnLength;
    /**
     * 列备注
     */
    private String columnComment;
    /**
     * 列主键
     */
    private String columnKey;
    /**
     * 列扩展信息
     */
    private String extra;
    /**
     * 属性名称(第一个字母大写)
     */
    private String attrName;
    /**
     * 属性名称(第一个字母小写)
     */
    private String attrname;
    /**
     * 属性类型
     */
    private String attrType;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getColumnLength() {
        return columnLength;
    }

    public void setColumnLength(String columnLength) {
        this.columnLength = columnLength;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrname() {
        return attrname;
    }

    public void setAttrname(String attrname) {
        this.attrname = attrname;
    }

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }
}
