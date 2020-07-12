package com.spring.sys.service;

import com.spring.sys.pojo.SysExpInfo;
import com.spring.sys.pojo.SysLogInfo;

import java.util.List;
import java.util.Map;

/**
 * 系统异常
 *
 * @author  团子
 * @date 2018/4/10 19:56
 * @since V1.0
 */
public interface SysExpService {

    List<SysExpInfo> queryList(Map<String, Object> map);

    void save(SysExpInfo sysLog);

}
