package com.spring.job.service.impl;


import com.spring.job.dao.SysJobLogDao;
import com.spring.job.pojo.SysJobLogInfo;
import com.spring.job.service.SysJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 定时Job日志
 * @author  团子
 * @since V1.0
 * @date 2018/4/18 17:22
 */
@Service("sysJobLogService")
public class SysJobLogServiceImpl implements SysJobLogService {
	@Autowired
	private SysJobLogDao sysJobLogDao;
	
	@Override
	public List<SysJobLogInfo> queryList(Map<String, Object> map) {
		return sysJobLogDao.queryList(map);
	}

	@Override
	public void save(SysJobLogInfo log) {
		sysJobLogDao.save(log);
	}

}
