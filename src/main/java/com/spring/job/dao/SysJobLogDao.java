package com.spring.job.dao;


import com.spring.common.mvc.BaseDao;
import com.spring.job.pojo.SysJobLogInfo;
import org.springframework.stereotype.Repository;

/**
 * 定时Job日志
 * @author  团子
 * @since V1.0
 * @date 2018/4/18 17:22
 */
@Repository
public interface SysJobLogDao extends BaseDao<SysJobLogInfo> {
	
}
