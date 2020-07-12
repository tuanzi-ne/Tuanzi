package com.spring.job.service;

import com.spring.job.pojo.SysJobLogInfo;

import java.util.List;
import java.util.Map;

/**
 * 定时Job日志
 * @author  团子
 * @since V1.0
 * @date 2018/4/18 15:46
 */
public interface SysJobLogService {

	/**
	 * 查询定时Job日志列表
	 */
	List<SysJobLogInfo> queryList(Map<String, Object> map);

	/**
	 * 保存定时Job日志
	 */
	void save(SysJobLogInfo info);
	
}
