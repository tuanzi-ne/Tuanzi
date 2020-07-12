package com.spring.common.mvc;

import java.util.List;
import java.util.Map;

/**
 * dao基础类
 *
 * @author  团子
 * @since: V1.0
 * @date 2018/3/2 13:45
 */
public interface BaseDao<T> {

    /**
     * 查询
     */
    List<T> queryList(Map<String, Object> map);

    /**
     * 批量查询
     */
    List<T> queryListBatch(Object[] t);

    /**
     * 根据Id查询
     */
    T queryObject(Object id);

    /**
     * 保存新增
     */
    void save(T t);

    /**
     * 更新
     */
    void update(T t);

    /**
     * 批量删除
     */
    void delete(Object[] t);

    /**
     * 删除
     */
    void delete(Object t);
}
