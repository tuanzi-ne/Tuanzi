package com.spring.sys.controller;

import com.github.pagehelper.PageInfo;
import com.spring.common.annotation.SysLog;
import com.spring.common.utils.PageUtils;
import com.spring.common.utils.Result;
import com.spring.common.validator.ValidatorUtils;
import com.spring.common.validator.group.AddGroup;
import com.spring.common.validator.group.UpdateGroup;
import com.spring.sys.pojo.SysParamInfo;
import com.spring.sys.service.SysParamService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 系统参数
 * 
 * @author  团子
 * @date 2018-08-26 08:14:13
 * @since V1.0
 */
@Controller
@RequestMapping("/sys/param")
public class SysParamController {
	@Autowired
	private SysParamService sysParamService;

    @GetMapping
    String param() {
        return "/sys/param";
    }
	/**
	 * 列表
	 */
    @SysLog("查询参数")
    @RequiresPermissions("sys:param:list")
	@GetMapping("/list")
    @ResponseBody
	public Result list(@RequestParam Map<String, Object> params){
        // 分页
        PageUtils.initPaging(params);
        // 查询数据
        List<SysParamInfo> list = sysParamService.queryList(params);
        PageInfo page = new PageInfo(list);
        HashMap dataMap = new HashMap(20);
        dataMap.put("rows", page.getList());
        dataMap.put("total", page.getTotal());
        return Result.datas(dataMap);
	}

    /**
     * 验证是否存在参数键名
     */
    @PostMapping("/isHasKey")
    @ResponseBody
    public boolean isHasKey(@RequestParam Map<String, Object> params) {
        return !sysParamService.isHasKey(params);
    }


    /**
     * 新增
     */
    @RequiresPermissions("sys:param:add")
    @GetMapping("/add")
    public String add() {
        return "sys/paramEdit";
    }

    /**
     * 保存新增
     */
    @SysLog("新增参数")
    @RequiresPermissions("sys:param:add")
    @PostMapping("/save")
    @ResponseBody
    public Result save(@RequestBody SysParamInfo sysParam) {
        ValidatorUtils.validate(sysParam, AddGroup.class);
        sysParamService.save(sysParam);
        return Result.build();
    }

    /**
     * 修改信息
     */
    @RequiresPermissions("sys:param:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        SysParamInfo info = sysParamService.querySysParamById(id);
        model.addAttribute("paramInfo", info);
        return "sys/paramEdit";
    }

    /**
     * 保存修改
     */
    @SysLog("修改参数")
    @RequiresPermissions("sys:param:edit")
    @PostMapping("/update")
    @ResponseBody
    public Result update(@RequestBody SysParamInfo sysParam) {
        ValidatorUtils.validate(sysParam, UpdateGroup.class);
        sysParamService.update(sysParam);
        return Result.build();
    }

    /**
     * 删除
     */
    @SysLog("删除参数")
    @RequiresPermissions("sys:param:delete")
    @PostMapping("/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        sysParamService.delete(ids);
        return Result.build();
    }

}
