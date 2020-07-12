package ${package!}.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.spring.common.annotation.SysLog;
import com.spring.common.validator.ValidatorUtils;
import com.spring.common.validator.group.AddGroup;
import com.spring.common.validator.group.UpdateGroup;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.spring.common.utils.PageUtils;
import com.spring.common.utils.Result;
import ${package!}.pojo.${className!}Info;
import ${package!}.service.${className!}Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * ${comments!}
 * 
 * @author ${author!}
 * @date ${datetime!}
 * @since V1.0
 */
@Controller
@RequestMapping("/${pathName!}")
public class ${className!}Controller {
	@Autowired
	private ${className!}Service ${classname!}Service;

    @GetMapping
    String ${className!}() {
        return "${packageDir!}/${webPagename!}";
    }

	
	/**
	 * 列表
	 */
    @SysLog("查询${comments!}")
    @RequiresPermissions("${permPrefix!}:list")
	@GetMapping("/list")
    @ResponseBody
	public Result list(@RequestParam Map<String, Object> params){
        // 分页
        PageUtils.initPaging(params);
        // 查询数据
        List<${className!}Info> list = ${classname!}Service.queryList(params);
        PageInfo page = new PageInfo(list);
        HashMap dataMap = new HashMap();
        dataMap.put("rows", page.getList());
        dataMap.put("total", page.getTotal());
        return Result.datas(dataMap);
	}

    /**
     * 新增
     */
    @RequiresPermissions("${permPrefix!}:add")
    @GetMapping("/add")
    public String add() {
        return "${packageDir!}/${webPagename!}Edit";
    }

    /**
     * 保存新增
     */
    @SysLog("新增${comments!}")
    @RequiresPermissions("${permPrefix!}:add")
    @PostMapping("/save")
    @ResponseBody
    public Result save(@RequestBody ${className!}Info ${classname!}) {
        ValidatorUtils.validate(${classname!}, AddGroup.class);
        ${classname!}Service.save(${classname!});
        return Result.build();
    }

    /**
     * 修改信息
     */
    @RequiresPermissions("${permPrefix!}:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        ${className!}Info info = ${classname!}Service.get${className!}ById(id);
        model.addAttribute("${webPagename!}", info);
        return "${packageDir!}/${webPagename!}Edit";
    }

    /**
     * 保存修改
     */
    @SysLog("修改${comments!}")
    @RequiresPermissions("${permPrefix!}:edit")
    @PostMapping("/update")
    @ResponseBody
    public Result update(@RequestBody ${className!}Info ${classname!}) {
        ValidatorUtils.validate(${classname!}, UpdateGroup.class);
        ${classname!}Service.update(${classname!});
        return Result.build();
    }

    /**
     * 删除
     */
    @SysLog("删除${comments!}")
    @RequiresPermissions("${permPrefix!}:delete")
    @PostMapping("/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        ${classname!}Service.delete(ids);
        return Result.build();
    }

}
