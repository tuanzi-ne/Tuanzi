package com.spring.job.service.impl;

import com.spring.common.utils.Constants;
import com.spring.job.dao.SysJobDao;
import com.spring.job.pojo.SysJobInfo;
import com.spring.job.service.SysJobService;
import com.spring.job.utils.QuartzUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("sysJobService")
public class SysJobServiceImpl implements SysJobService {
	@Autowired
    private Scheduler scheduler;
	@Autowired
	private SysJobDao sysJobDao;
	
	/**
	 * 项目启动时，初始化定时器
	 */
	@PostConstruct
	public void init(){
		List<SysJobInfo> sysJobList = sysJobDao.queryList(new HashMap<String, Object>());
		for(SysJobInfo sysJob : sysJobList){
			CronTrigger cronTrigger = QuartzUtils.getCronTrigger(scheduler, sysJob.getId());
            if(cronTrigger == null) {
                QuartzUtils.addJob(scheduler, sysJob);
            }else {
                QuartzUtils.updateJob(scheduler, sysJob);
            }
		}
	}
	
	@Override
	public List<SysJobInfo> queryList(Map<String, Object> map) {
		return sysJobDao.queryList(map);
	}

    /**
     * 验证是否存在用户
     */
    @Override
    public boolean hasJob(Map<String, Object> map) {
        return sysJobDao.hasJob(map) > 0;
    }

    /**
     * 根据Id获取信息
     */
    @Override
    public SysJobInfo getJobByUId(Integer jobId) {
        Map<String, Object> map = new HashMap<>();
        map.put("jobId", jobId);
        List<SysJobInfo> list = sysJobDao.queryList(map);
        return list.get(0);
    }

	@Override
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	public void save(SysJobInfo sysJob) {
		sysJob.setCreateTime(new Date());
		sysJob.setUpdateTime(new Date());
		sysJob.setStatus(Constants.JobStatus.NORMAL.getValue());
        sysJobDao.save(sysJob);
        QuartzUtils.addJob(scheduler, sysJob);
    }
	
	@Override
	@Transactional(rollbackFor = Exception.class, value = "transactionManager")
	public void update(SysJobInfo sysJob) {
		sysJob.setUpdateTime(new Date());
        sysJobDao.update(sysJob);
        QuartzUtils.updateJob(scheduler, sysJob);
    }

	@Override
    @Transactional(rollbackFor = Exception.class, value = "transactionManager")
    public void delete(Integer[] jobIds) {
    	for(Integer jobId : jobIds){
    		QuartzUtils.deleteJob(scheduler, jobId);
    	}
    	//删除数据
    	sysJobDao.delete(jobIds);
	}

    private int updateBatch(Integer[] jobIds, int status){
    	Map<String, Object> map = new HashMap<>();
    	map.put("list", jobIds);
    	map.put("status", status);
    	return sysJobDao.updateBatch(map);
    }
	/**
	 * 立即执行
	 */
	@Override
    @Transactional(rollbackFor = Exception.class, value = "transactionManager")
    public void runJob(Integer[] jobIds) {
    	for(Integer jobId : jobIds){
    		QuartzUtils.runJob(scheduler, getJobByUId(jobId));
    	}
    }
	/**
	 * 暂停运行
	 */
	@Override
    @Transactional(rollbackFor = Exception.class, value = "transactionManager")
    public void pauseJob(Integer[] jobIds) {
        for(Integer jobId : jobIds){
    		QuartzUtils.pauseJob(scheduler, jobId);
    	}
    	updateBatch(jobIds, Constants.JobStatus.PAUSE.getValue());
    }
	/**
	 * 恢复运行
	 */
	@Override
    @Transactional(rollbackFor = Exception.class, value = "transactionManager")
    public void resumeJob(Integer[] jobIds) {
    	for(Integer jobId : jobIds){
    		QuartzUtils.resumeJob(scheduler, jobId);
    	}
    	updateBatch(jobIds, Constants.JobStatus.NORMAL.getValue());
    }
    
}
