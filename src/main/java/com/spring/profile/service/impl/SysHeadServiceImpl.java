package com.spring.profile.service.impl;

import com.spring.common.mvc.AbstractLogging;
import com.spring.profile.dao.SysHeadDao;
import com.spring.profile.pojo.SysHeadInfo;
import com.spring.profile.service.SysHeadService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Title: SysHeadServiceImpl
 * @Description: 系统头像
 * @author  团子
 * @date 2018/9/2 9:19
 * @since v1.0
 */
@Service("SysHeadService")
public class SysHeadServiceImpl extends AbstractLogging implements SysHeadService {
    @Autowired
    SysHeadDao sysHeadDao;
    /**
     * 根据id查询头像
     */
    @Override
    public SysHeadInfo queryHeadInfoById(Integer userId){
         return sysHeadDao.queryHeadInfoById(userId);
    }

    /**
     * 保存头像
     * @param head
     */
    @Override
    public void save(SysHeadInfo head) {
        //先删除用户ID的头像记录
        log.info("=====删除用户ID为{}的头像记录,开始时间:{}=====", head.getUserId(), new DateTime().toString("yyyy-MM-dd HH:mm:ss.SSS"));
        sysHeadDao.delete(head.getUserId());
        log.info("=====删除用户ID为{}的头像记录,结束时间:{}=====", head.getUserId(), new DateTime().toString("yyyy-MM-dd HH:mm:ss.SSS"));
        head.setCreateTime(new Date());
        head.setUpdateTime(new Date());
        //再插入用户ID的头像记录
        log.info("=====插入用户ID为{}的头像记录,开始时间:{}=====", head.getUserId(), new DateTime().toString("yyyy-MM-dd HH:mm:ss.SSS"));
        sysHeadDao.save(head);
        log.info("=====插入用户ID为{}的头像记录,结束时间:{}=====", head.getUserId(), new DateTime().toString("yyyy-MM-dd HH:mm:ss.SSS"));
    }
}
