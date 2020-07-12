package com.spring.gen.service;

import com.spring.gen.pojo.ColumnInfo;
import com.spring.gen.pojo.TableInfo;

import java.util.List;
import java.util.Map;

/**
 * 代码生成器Service接口
 * @author  团子
 * @since V1.0
 * @date 2018/4/12 17:32
 */
public interface SysGenService {
	
	List<TableInfo> queryList(Map<String, Object> map);

	TableInfo queryTable(String tableName);

	List<ColumnInfo> queryColumns(String tableName);
	
	/**
	 * 生成代码
	 */
	byte[] genCode(String[] tableNames);
}
