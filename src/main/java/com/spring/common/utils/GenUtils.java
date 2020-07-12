package com.spring.common.utils;


import com.spring.common.exception.SysException;
import com.spring.gen.pojo.ColumnInfo;
import com.spring.gen.pojo.TableInfo;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.joda.time.DateTime;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器工具类
 *
 * @author  团子
 * @date 2018/4/12 16:48
 * @since V1.0
 */
public class GenUtils {
    /**
     * 获取配置信息
     */
    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("config/gen.properties");
        } catch (ConfigurationException e) {
            throw new SysException("获取代码生成配置文件失败!", e);
        }
    }

    /**
     * 获取模板名称列表
     */
    private static List<String> getTemplateNames() {
        List<String> templateNames = new ArrayList<>();
        templateNames.add("Info.java.ftl");
        templateNames.add("Dao.java.ftl");
        templateNames.add("Dao.xml.ftl");
        templateNames.add("Service.java.ftl");
        templateNames.add("ServiceImpl.java.ftl");
        templateNames.add("Controller.java.ftl");
        templateNames.add("list.html.ftl");
        templateNames.add("list.js.ftl");
        templateNames.add("edit.html.ftl");
        templateNames.add("edit.js.ftl");
        templateNames.add("res.sql.ftl");
        return templateNames;
    }

    /**
     * 生成代码
     */
    public static void genCode(TableInfo tableInfo,
                               List<ColumnInfo> columns, ZipOutputStream zipOutPut) {
        //配置信息
        Configuration config = getConfig();

        //设置首字母大写类名
        String classNamePrefix = config.getString("classname.prefix");
        tableInfo.setClassName(classNamePrefix);
        tableInfo.setClassname(StringUtils.uncapitalize(classNamePrefix));
        //设置webPage,首字母小写
        String webPagename = StringUtils.uncapitalize(config.getString("webpage.name"));
        //设置webPage,首字母大写
        String webPageName = StringUtils.capitalize(config.getString("webpage.name"));

        //列信息
        List<ColumnInfo> columsList = new ArrayList<>();
        for (ColumnInfo column : columns) {
            //列名转换成Java属性名
            String attrName = columnToJava(column.getColumnName());
            //设置属性名
            column.setAttrName(attrName);
            column.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = config.getString(column.getDataType(), "unknowType");
            column.setAttrType(attrType);

            //过滤列注释
            column.setColumnComment(filterColumnComment(column.getColumnComment()));
            //是否主键
            if ("PRI".equalsIgnoreCase(column.getColumnKey()) && tableInfo.getPk() == null) {
                tableInfo.setPk(column);
            }
            columsList.add(column);
        }
        tableInfo.setColumns(columsList);

        //如果没主键将第一个字段设为主键
        if (tableInfo.getPk() == null) {
            tableInfo.setPk(tableInfo.getColumns().get(0));
        }

        //封装模板数据
        Map<String, Object> map = new HashMap<>(5);
        String author = config.getString("author").toLowerCase();
        String packageName = config.getString("package").toLowerCase();
        String permPrefix = config.getString("perm.prefix").toLowerCase();
        String packageDir = StringUtils.substringAfterLast(packageName, ".").toLowerCase();
        map.put("tableName", tableInfo.getTableName());
        map.put("comments", tableInfo.getTableComment().replace("表", ""));
        map.put("pk", tableInfo.getPk());
        map.put("className", tableInfo.getClassName());
        map.put("classname", tableInfo.getClassname());
        map.put("webPageName", webPageName);
        map.put("webPagename", webPagename);
        map.put("permPrefix", permPrefix);
        map.put("pathName", tableInfo.getTableName().replace("_", "/").toLowerCase());
        map.put("columns", tableInfo.getColumns());
        map.put("author", author);
        map.put("package", packageName);
        map.put("packageDir", packageDir);
        map.put("datetime", new DateTime().toString("yyyy-MM-dd HH:mm:ss"));

        //获取模板列表
        List<String> templateNames = getTemplateNames();
        for (String templateName : templateNames) {
            //渲染模板
            StringWriter sw = new StringWriter();
            FreeMarkerUtils.process(templateName, map, sw);
            try {
                zipOutPut.putNextEntry(new ZipEntry(getFileName(templateName, tableInfo, packageName, packageDir, webPageName, webPagename)));
                IOUtils.write(sw.toString(), zipOutPut, "UTF-8");
                IOUtils.closeQuietly(sw);
                zipOutPut.closeEntry();
            } catch (Exception e) {
                throw new SysException("打包模板失败,表名：" + tableInfo.getTableName(), e);
            }
        }
    }


    /**
     * 列名转换成Java属性名
     */
    private static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 过滤列注释
     */
    private static String filterColumnComment(String comment) {
        return StringUtils.substringBefore(comment, ",");
    }


    /**
     * 获取文件名
     */
    private static String getFileName(String templateName, TableInfo info,
                                      String packageName, String packageDir,
                                      String webPageName, String webPagename) {
        String className = info.getClassName();
        String packagePath = "main" + File.separator + "java" + File.separator;
        if (StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }

        if (templateName.contains("Info.java.ftl")) {
            return new StringBuffer().append(packagePath).
                    append("pojo").
                    append(File.separator).
                    append(className).
                    append("Info.java").toString();
        }

        if (templateName.contains("Dao.java.ftl")) {
            return new StringBuffer().append(packagePath).
                    append("dao").
                    append(File.separator).
                    append(className).
                    append("Dao.java").toString();
        }

        if (templateName.contains("Service.java.ftl")) {
            return new StringBuffer().append(packagePath).
                    append("service").
                    append(File.separator).
                    append(className).
                    append("Service.java").toString();
        }

        if (templateName.contains("ServiceImpl.java.ftl")) {
            return new StringBuffer().append(packagePath).
                    append("service").
                    append(File.separator).
                    append("impl").
                    append(File.separator).
                    append(className).
                    append("ServiceImpl.java").toString();
        }

        if (templateName.contains("Controller.java.ftl")) {
            return new StringBuffer().append(packagePath).
                    append("controller").
                    append(File.separator).
                    append(className).
                    append("Controller.java").toString();
        }

        if (templateName.contains("Dao.xml.ftl")) {
            return new StringBuffer().append("main").
                    append(File.separator).
                    append("resources").
                    append(File.separator).
                    append("mapper").
                    append(File.separator).
                    append(className).
                    append("Dao.xml").toString();
        }

        if (templateName.contains("list.html.ftl")) {
            return new StringBuffer().append("main").
                    append(File.separator).
                    append("resources").
                    append(File.separator).
                    append("templates").
                    append(File.separator).
                    append(packageDir).
                    append(File.separator).
                    append(webPagename).
                    append(".html").toString();
        }

        if (templateName.contains("list.js.ftl")) {
            return new StringBuffer().append("main").
                    append(File.separator).
                    append("resources").
                    append(File.separator).
                    append("static").
                    append(File.separator).
                    append("js").
                    append(File.separator).
                    append(packageDir).
                    append(File.separator).
                    append(webPagename).
                    append(".js").toString();
        }

        if (templateName.contains("edit.html.ftl")) {
            return new StringBuffer().append("main").
                    append(File.separator).
                    append("resources").
                    append(File.separator).
                    append("templates").
                    append(File.separator).
                    append(packageDir).
                    append(File.separator).
                    append(webPagename).
                    append("Edit.html").toString();
        }

        if (templateName.contains("edit.js.ftl")) {
            return new StringBuffer().append("main").
                    append(File.separator).
                    append("resources").
                    append(File.separator).
                    append("static").
                    append(File.separator).
                    append("js").
                    append(File.separator).
                    append(packageDir).
                    append(File.separator).
                    append(webPagename).
                    append("Edit.js").toString();
        }

        if (templateName.contains("res.sql.ftl")) {
            return "res.sql";
        }

        return null;
    }
}
