package com.spring.sys.service.impl;

import com.spring.common.mvc.AbstractLogging;
import com.spring.common.utils.Constants;
import com.spring.sys.dao.SysResDao;
import com.spring.sys.pojo.SysResInfo;
import com.spring.sys.service.SysResService;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 * 系统资源管理service接口实现类
 *
 * @author  团子
 * @since: V1.0
 * @date 2018/3/2 13:37
 */
@Service("sysResService")
public class SysResServiceImpl extends AbstractLogging implements SysResService {
    @Autowired
    private SysResDao sysResDao;


    /**
     * 查询资源列表
     */
    @Override
    public List<SysResInfo> queryList(Map<String, Object> map) {
        return sysResDao.queryList(map);
    }

    /**
     * 获取用户菜单列表
     */
    @Override
    public  List<SysResInfo> getNav(Integer userId) {
        //管理员授权资源
        if (Objects.equals(userId, Constants.ADMIN_ID)) {
            return getResList(null);
        }
        //普通用户授权资源
        List<Integer> resIdList = sysResDao.getResIdByUId(userId);
        return getResList(resIdList);
    }

    /**
     * 获取所有授权资源列表
     */
    private List<SysResInfo> getResList(List<Integer> resIdList) {
        //获取一级授权资源列表
        List<SysResInfo> resList = getSubResByPId(Constants.RES_TOP_ID, resIdList);
        //获取所有授权子资源列表
        getSubResList(resList, resIdList);
        return resList;
    }

    /**
     * 查询和过滤授权资源列表
     */
    private List<SysResInfo> getSubResByPId(Integer parentId, List<Integer> resIdList) {
        List<SysResInfo> resList = getResByPId(parentId);
        // 管理员过滤
        if (resIdList == null) {
            return resList;
        }
        // 普通用户过滤
        List<SysResInfo> userResList = new ArrayList<>();
        for (SysResInfo res : resList) {
            if (resIdList.contains(res.getId())) {
                userResList.add(res);
            }
        }
        return userResList;
    }

    /**
     * 递归查询子资源列表
     */
    private List<SysResInfo> getSubResList(List<SysResInfo> resList, List<Integer> resIdList) {
        List<SysResInfo> subResList = new ArrayList<>();
        for (SysResInfo info : resList) {
            //目录
            if (info.getResType() == Constants.ResType.CATALOG.getValue()) {
                info.setList(getSubResList(getSubResByPId(info.getId(), resIdList), resIdList));
            }
            subResList.add(info);
        }
        return subResList;
    }

    /**
     * 验证是否存在资源
     */
    @Override
    public boolean hasRes(Map<String, Object> map) {
        Integer[] resTypeArray = {Constants.ResType.CATALOG.getValue(), Constants.ResType.MENU.getValue()};
        map.put("resTypeArray",resTypeArray);
        return sysResDao.hasRes(map) > 0;
    }

    /**
     * 保存资源
     */
    @Override
    @Transactional(rollbackFor = Exception.class, value = "transactionManager")
    public void save(SysResInfo res) {
        res.setCreateTime(new Date());
        res.setUpdateTime(new Date());
        sysResDao.save(res);
    }

    /**
     * 根据上级资源ID查所有子资源
     */
    private List<SysResInfo> getResByPId(Integer parentId) {
        return sysResDao.getResByPId(parentId);
    }

    /**
     * 根据resId获取资源信息
     */
    @Override
    public SysResInfo getResByUId(Integer resId) {
        Map<String, Object> map = new HashMap<>();
        map.put("resId", resId);
        List<SysResInfo> resList = sysResDao.queryList(map);
        return resList.get(0);
    }

    /**
     * 保存资源修改
     */
    @Override
    @Transactional(rollbackFor = Exception.class, value = "transactionManager")
    public void update(SysResInfo res) {
        res.setUpdateTime(new Date());
        sysResDao.update(res);
    }

    /**
     * 删除资源
     */
    @Override
    @Transactional(rollbackFor = Exception.class, value = "transactionManager")
    public void delete(Integer[] ids) {
        log.info("=====删除用户{},开始时间:{}=====", StringUtils.join(ids, ","), new DateTime().toString("yyyy-MM-dd HH:mm:ss.SSS"));
        sysResDao.delete(ids);
        log.info("=====删除用户{},结束时间:{}=====", StringUtils.join(ids, ","), new DateTime().toString("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    /**
     * 选择资源
     */
    @Override
    public List<SysResInfo> selectResById(String resId) {
        Map<String, Object> params = new HashMap<>(50);
        Integer[] resTypeArray = {Constants.ResType.CATALOG.getValue(), Constants.ResType.MENU.getValue()};
        params.put("resTypeArray", resTypeArray);
        List<SysResInfo> resList = sysResDao.queryList(params);
        //添加顶级菜资源
        SysResInfo root = new SysResInfo();
        root.setId(Constants.RES_TOP_ID);
        root.setResName("顶级资源");
        root.setParentId(-1);
        root.setOpen(true);
        resList.add(root);
        if (resId != null && !resId.isEmpty()) {
            List<SysResInfo> newResList = getSubList(resList, Integer.valueOf(resId));
            if (resList.size() == newResList.size()) {
                for (SysResInfo info : resList) {
                    if (resId.equals(info.getId() + "")
                            && info.getParentId().intValue() == Constants.DPT_TOP_ID.intValue()) {
                        resList.clear();
                        resList.add(info);
                        break;
                    }
                }
            } else {
                resList.removeAll(newResList);
            }
        }
        return resList;
    }
    /**
     * 递归查询本资源及其子资源列表
     */
    private List<SysResInfo> getSubList(List<SysResInfo> resList, Integer pId) {
        List<SysResInfo> subList = new ArrayList<>();
        resList.forEach(info -> {
            if (info.getId().intValue() == pId) {
                subList.add(info);
            }
            if (info.getParentId().intValue() == pId) {
                subList.addAll(getSubList(resList, info.getId()));
            }
        });
        return subList;
    }
}
