package com.spring.job.dao;


import com.spring.common.mvc.BaseDao;
import com.spring.job.pojo.SysJobInfo;
import org.springframework.stereotype.Repository;

import java.util.Map;


/**
 * 定时任务Dao接口
 * @author  团子
 * @since V1.0
 * @date 2018/4/18 17:01
 */
@Repository
public interface SysJobDao extends BaseDao<SysJobInfo> {

    /**
     * 是否存在名称
     */
    Integer hasJob(Map<String, Object> map);
	
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);

}
