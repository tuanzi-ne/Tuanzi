package com.spring.job.service;

import com.spring.job.pojo.SysJobInfo;

import java.util.List;
import java.util.Map;

/**
 * 定时任务Service接口
 * @author  团子
 * @since V1.0
 * @date 2018/4/18 16:22
 */
public interface SysJobService {

	/**
	 * 查询定时任务列表
	 */
	List<SysJobInfo> queryList(Map<String, Object> map);

    /**
     * 验证是否存在用户
     */
    boolean hasJob(Map<String, Object> map);

	/**
	 * 根据Id获取用户信息
	 */
    SysJobInfo getJobByUId(Integer jobId);

	/**
	 * 保存定时任务
	 */
	void save(SysJobInfo sysJob);
	
	/**
	 * 更新定时任务
	 */
	void update(SysJobInfo sysJob);
	
	/**
	 * 批量删除定时任务
	 */
	void delete(Integer[] jobIds);
	
	/**
	 * 立即执行
	 */
	void runJob(Integer[] jobIds);
	
	/**
	 * 暂停运行
	 */
	void pauseJob(Integer[] jobIds);
	
	/**
	 * 恢复运行
	 */
	void resumeJob(Integer[] jobIds);
}
