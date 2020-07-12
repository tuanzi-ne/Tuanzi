package com.spring.sys.controller;

import com.spring.common.annotation.SysLog;
import com.spring.common.mvc.BaseController;
import com.spring.common.utils.PageUtils;
import com.spring.common.utils.Result;
import com.spring.common.validator.ValidatorUtils;
import com.spring.common.validator.group.AddGroup;
import com.spring.common.validator.group.UpdateGroup;
import com.spring.sys.pojo.SysResInfo;
import com.spring.sys.service.SysResService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统资源管理
 *
 * @author  团子
 * @date 2018/3/1 17:44
 * @since V1.0
 */
@Controller
@RequestMapping("/sys/res")
public class SysResController extends BaseController {
    @Autowired
    private SysResService sysResService;

    @GetMapping
    String res() {
        return "/sys/res";
    }
    /**
     * 查询所有资源
     *
     * @param params 查询参数
     */
    @SysLog("查询资源")
    @RequiresPermissions("sys:res:list")
    @GetMapping("/list")
    @ResponseBody
    public List<SysResInfo> list(@RequestParam Map<String, Object> params) {
        // 查询数据
        List<SysResInfo> list = sysResService.queryList(PageUtils.filterParams(params));
        return list;
    }

    /**
     * 新增
     */
    @RequiresPermissions("sys:res:add")
    @GetMapping("/add")
    public String add() {
        return "sys/resEdit";
    }

    /**
     * 验证是否存在资源
     */
    @PostMapping("/hasRes")
    @ResponseBody
    boolean hasRes(@RequestParam Map<String, Object> params) {
        return !sysResService.hasRes(params);
    }

    /**
     * 获取资源tree
     */
    @PostMapping("/tree")
    @ResponseBody
    public Result tree(@RequestParam String resId) {
        List<SysResInfo> resList = sysResService.selectResById(resId);
        return Result.data("resList", resList);
    }

    /**
     * 保存新增
     */
    @SysLog("新增资源")
    @RequiresPermissions("sys:res:add")
    @PostMapping("/save")
    @ResponseBody
    public Result save(@RequestBody SysResInfo res) {
        ValidatorUtils.validate(res, AddGroup.class);
        sysResService.save(res);
        return Result.build();
    }

    /**
     * 修改，获取资源信息
     */
    @RequiresPermissions("sys:res:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        SysResInfo uinfo = sysResService.getResByUId(id);
        model.addAttribute("res", uinfo);
        return "sys/resEdit";
    }

    /**
     * 保存资源修改
     */
    @SysLog("编辑资源")
    @RequiresPermissions("sys:res:edit")
    @PostMapping("/update")
    @ResponseBody
    public Result update(@RequestBody SysResInfo res) {
        ValidatorUtils.validate(res, UpdateGroup.class);
        sysResService.update(res);
        return Result.build();
    }

    /**
     * 删除资源
     */
    @SysLog("删除资源")
    @RequiresPermissions("sys:res:delete")
    @PostMapping("/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        //判断是否有子资源
        Map<String, Object> params = new HashMap<>(15);
        for (Integer resId:ids){
            params.put("parentId", resId);
            List<SysResInfo> resList = sysResService.queryList(params);
            if (resList.size() > 0) {
                return Result.error("请先删除子资源!");
            }
        }
        sysResService.delete(ids);
        return Result.build();
    }
}
