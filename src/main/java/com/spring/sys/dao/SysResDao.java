package com.spring.sys.dao;

import com.spring.common.mvc.BaseDao;
import com.spring.sys.pojo.SysResInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

/**
 * 系统资源管理Dao接口
 *
 * @author  团子
 * @since: V1.0
 * @date 2018/3/2 13:37
 */
@Repository
public interface SysResDao extends BaseDao<SysResInfo> {

    /**
     * 是否存在资源
     */
    Integer hasRes(Map<String, Object> map);

    /**
     * 根据用户ID查资源ID
     */
    List<Integer> getResIdByUId(@Param("userId") Integer userId);

    /**
     * 根据上级资源ID查所有子资源
     */
    List<SysResInfo> getResByPId(Integer userId);
}
