package com.spring.sys.service.impl;

import com.spring.sys.dao.SysLogDao;
import com.spring.sys.pojo.SysLogInfo;
import com.spring.sys.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;


@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogDao sysLogDao;

    @Override
    public List<SysLogInfo> queryList(Map<String, Object> map) {
        return sysLogDao.queryList(map);
    }

    @Override
    public void save(SysLogInfo sysLog) {
        sysLogDao.save(sysLog);
    }


}
