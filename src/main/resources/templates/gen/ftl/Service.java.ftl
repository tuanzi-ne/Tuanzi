package ${package!}.service;

import ${package!}.pojo.${className!}Info;
import java.util.List;
import java.util.Map;

/**
 * ${comments!}
 *
 * @author ${author!}
 * @date ${datetime!}
 * @since V1.0
 */
public interface ${className!}Service {
	/**
     * 查询
     */
	List<${className!}Info> queryList(Map<String, Object> map);

    /**
     * 根据Id查询
     */
    ${className!}Info get${className!}ById(${(pk.attrType)!} ${(pk.attrname)!});

    /**
     * 保存
     */
	void save(${className!}Info ${classname!});

    /**
     * 更新
     */
	void update(${className!}Info ${classname!});

    /**
     * 批量删除
     */
	void delete(${(pk.attrType)!}[] ${(pk.attrname)!}s);
}
