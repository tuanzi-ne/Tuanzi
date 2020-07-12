package com.spring.sys.service.impl;

import com.spring.sys.dao.SysExpDao;
import com.spring.sys.pojo.SysExpInfo;
import com.spring.sys.service.SysExpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service("sysExpService")
public class SysExpServiceImpl implements SysExpService {
    @Autowired
    private SysExpDao sysExpDao;

    @Override
    public List<SysExpInfo> queryList(Map<String, Object> map) {
        return sysExpDao.queryList(map);
    }

    @Override
    public void save(SysExpInfo sysExpInfo) {
        sysExpDao.save(sysExpInfo);
    }

}
