package ${package!}.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ${package!}.dao.${className!}Dao;
import ${package!}.pojo.${className!}Info;
import ${package!}.service.${className!}Service;


/**
 * ${comments!}
 *
 * @author ${author!}
 * @date ${datetime!}
 * @since V1.0
 */
@Service("${classname!}Service")
public class ${className!}ServiceImpl implements ${className!}Service {

	@Autowired
	private ${className!}Dao ${classname!}Dao;
	
	/**
     * 查询
     */
	@Override
	public List<${className!}Info> queryList(Map<String, Object> map) {
		return ${classname!}Dao.queryList(map);
	}

    /**
     * 根据Id查询
     */
    @Override
    public ${className!}Info get${className!}ById(${(pk.attrType)!} ${(pk.attrname)!}) {
        Map map = new HashMap(1);
        map.put("id", id);
        return (${className!}Info)${classname!}Dao.queryList(map).get(0);
    }

    /**
     * 保存新增
     */
	@Override
	public void save(${className!}Info ${classname!}) {
        ${classname!}.setCreateTime(new Date());
        ${classname!}.setUpdateTime(new Date());
		${classname!}Dao.save(${classname!});
	}

    /**
     * 更新
     */
	@Override
	public void update(${className!}Info ${classname!}) {
        ${classname!}.setUpdateTime(new Date());
		${classname!}Dao.update(${classname!});
    }

    /**
     * 批量删除
     */
    @Override
	public void delete(${(pk.attrType)!}[] ${(pk.attrname)!}s) {
		${classname!}Dao.delete(${(pk.attrname)!}s);
	}
}
