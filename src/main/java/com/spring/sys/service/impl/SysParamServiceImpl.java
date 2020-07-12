package com.spring.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.spring.sys.dao.SysParamDao;
import com.spring.sys.pojo.SysParamInfo;
import com.spring.sys.service.SysParamService;


/**
 * 系统参数
 *
 * @author  团子
 * @date 2018-08-26 08:14:13
 * @since V1.0
 */
@Service("sysParamService")
public class SysParamServiceImpl implements SysParamService {
    @Autowired
    private SysParamDao sysParamDao;

    /**
     * 查询
     */
    @Override
    public List<SysParamInfo> queryList(Map<String, Object> map) {
        return sysParamDao.queryList(map);
    }

    /**
     * 查询所有参数,不要在该类中调用该缓存方法，否则缓存不会生效
     */
    @Cacheable(value = "paramlist", key = "'params'" , unless = "#result.size() == 0")
    @Override
    public List<SysParamInfo> queryAllList() {
        return sysParamDao.queryAllList();
    }


    /**
     * 验证是否存在用户
     */
    @Override
    public boolean isHasKey(Map<String, Object> map) {
        return sysParamDao.isHasKey(map) > 0;
    }

    /**
     * 根据Id查询
     */
    @Override
    public SysParamInfo querySysParamById(Integer id) {
        Map map = new HashMap(1);
        map.put("id", id);
        return (SysParamInfo)sysParamDao.queryList(map).get(0);
    }

    /**
     * 保存新增
     */
    @CacheEvict(value = "paramlist", allEntries = true)
    @Override
    public void save(SysParamInfo sysParam) {
        sysParam.setCreateTime(new Date());
        sysParam.setUpdateTime(new Date());
        sysParamDao.save(sysParam);
    }

    /**
     * 更新
     */
    @CacheEvict(value = "paramlist", allEntries = true)
    @Override
    public void update(SysParamInfo sysParam) {
        sysParam.setUpdateTime(new Date());
        sysParamDao.update(sysParam);
    }

    /**
     * 批量删除
     */
    @CacheEvict(value = "paramlist", allEntries = true)
    @Override
    public void delete(Integer[] ids) {
        sysParamDao.delete(ids);
    }
}
