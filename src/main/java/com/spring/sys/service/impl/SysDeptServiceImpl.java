package com.spring.sys.service.impl;

import com.spring.common.mvc.AbstractLogging;
import com.spring.common.utils.Constants;
import com.spring.sys.dao.SysDeptDao;
import com.spring.sys.pojo.SysDeptInfo;
import com.spring.sys.service.SysDeptService;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 系统部门管理service接口实现类
 *
 * @author  团子
 * @since: V1.0
 * @date 2018/3/2 13:37
 */
@Service("sysDeptService")
public class SysDeptServiceImpl extends AbstractLogging implements SysDeptService {
    @Autowired
    private SysDeptDao sysDeptDao;


    /**
     * 查询部门列表
     */
    @Override
    public List<SysDeptInfo> queryList(Map<String, Object> map) {
        return sysDeptDao.queryList(map);
    }


    /**
     * 验证是否存在部门
     */
    @Override
    public boolean hasDept(Map<String, Object> map) {
        return sysDeptDao.hasDept(map) > 0;
    }

    /**
     * 保存部门
     */
    @Override
    @Transactional(rollbackFor = Exception.class, value = "transactionManager")
    public void save(SysDeptInfo dept) {
        dept.setCreateTime(new Date());
        dept.setUpdateTime(new Date());
        sysDeptDao.save(dept);
    }

    /**
     * 根据deptId获取部门信息
     */
    @Override
    public SysDeptInfo getDeptById(Integer deptId) {
        Map<String, Object> map = new HashMap<>(30);
        map.put("deptId", deptId);
        List<SysDeptInfo> deptList = sysDeptDao.queryList(map);
        SysDeptInfo uinfo = deptList.get(0);
        return uinfo;
    }

    /**
     * 根据deptId选择部门
     */
    @Override
    public List<SysDeptInfo> selectDeptById(String deptId) {
        List<SysDeptInfo> deptList = queryList(null);
        //添加顶级菜资源
        SysDeptInfo root = new SysDeptInfo();
        root.setId(Constants.RES_TOP_ID);
        root.setDeptName("顶级部门");
        root.setParentId(-1);
        root.setOpen(true);
        deptList.add(root);
        if (deptId != null && !deptId.isEmpty()) {
            List<SysDeptInfo> newDeptList = getSubDptList(deptList, Integer.valueOf(deptId));
            if (deptList.size() == newDeptList.size()) {
                for (SysDeptInfo info : deptList) {
                    if (deptId.equals(info.getId() + "")
                            && info.getParentId().intValue() == Constants.DPT_TOP_ID.intValue()) {
                        deptList.clear();
                        deptList.add(info);
                        break;
                    }
                }
            } else {
                deptList.removeAll(newDeptList);
            }
        }
        return deptList;
    }

    /**
     * 递归查询本部门及其子部门列表
     */
    private List<SysDeptInfo> getSubDptList(List<SysDeptInfo> dptList, Integer pId) {
        List<SysDeptInfo> dptSubList = new ArrayList<>();
        for (SysDeptInfo info : dptList) {
            if (info.getId().intValue() == pId) {
                dptSubList.add(info);
            }
            if (info.getParentId().intValue() == pId) {
                dptSubList.addAll(getSubDptList(dptList, info.getId()));
            }
        }
        return dptSubList;
    }

    /**
     * 保存部门修改
     */
    @Override
    @Transactional(rollbackFor = Exception.class, value = "transactionManager")
    public void update(SysDeptInfo dept) {
        dept.setUpdateTime(new Date());
        sysDeptDao.update(dept);
    }

    /**
     * 删除部门
     */
    @Override
    @Transactional(rollbackFor = Exception.class, value = "transactionManager")
    public void delete(Integer[] deptIds) {
        log.info("=====删除用户{},开始时间:{}=====", StringUtils.join(deptIds, ","), new DateTime().toString("yyyy-MM-dd HH:mm:ss.SSS"));
        sysDeptDao.delete(deptIds);
        log.info("=====删除用户{},结束时间:{}=====", StringUtils.join(deptIds, ","), new DateTime().toString("yyyy-MM-dd HH:mm:ss.SSS"));
    }

}
