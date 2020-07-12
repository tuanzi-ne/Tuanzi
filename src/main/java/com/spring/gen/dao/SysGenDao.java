package com.spring.gen.dao;

import com.spring.common.mvc.BaseDao;
import com.spring.gen.pojo.ColumnInfo;
import com.spring.gen.pojo.TableInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 代码生成器Dao接口
 * @author  团子
 * @since V1.0
 * @date 2018/4/12 16:49
 */
@Repository
public interface SysGenDao extends BaseDao<TableInfo> {

    TableInfo queryTable(String tableName);

    List<ColumnInfo> queryColumns(String tableName);
}
