package com.spring.gen.service.impl;


import com.spring.common.utils.GenUtils;
import com.spring.gen.dao.SysGenDao;
import com.spring.gen.pojo.ColumnInfo;
import com.spring.gen.pojo.TableInfo;
import com.spring.gen.service.SysGenService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器Service接口实现类
 *
 * @author  团子
 * @date 2018/4/12 17:32
 * @since V1.0
 */
@Service("sysGenService")
public class SysGenServiceImpl implements SysGenService {
    @Autowired
    private SysGenDao sysGenDao;

    @Override
    public List<TableInfo> queryList(Map<String, Object> map) {
        return sysGenDao.queryList(map);
    }

    @Override
    public TableInfo queryTable(String tableName) {
        return sysGenDao.queryTable(tableName);
    }

    @Override
    public List<ColumnInfo> queryColumns(String tableName) {
        return sysGenDao.queryColumns(tableName);
    }

    @Override
    public byte[] genCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutPut = new ZipOutputStream(outputStream);
        for (String tableName : tableNames) {
            //查询表信息
            TableInfo table = queryTable(tableName);
            //查询列信息
            List<ColumnInfo> columns = queryColumns(tableName);
            //生成代码
            GenUtils.genCode(table, columns, zipOutPut);
        }
        IOUtils.closeQuietly(zipOutPut);
        return outputStream.toByteArray();
    }

}
