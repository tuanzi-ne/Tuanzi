package com.spring.sys.controller;

import com.github.pagehelper.PageInfo;
import com.spring.common.annotation.SysLog;
import com.spring.common.mvc.BaseController;
import com.spring.common.utils.Constants;
import com.spring.common.utils.PageUtils;
import com.spring.common.utils.Result;
import com.spring.common.validator.ValidatorUtils;
import com.spring.common.validator.group.AddGroup;
import com.spring.common.validator.group.UpdateGroup;
import com.spring.sys.pojo.SysResInfo;
import com.spring.sys.pojo.SysRoleInfo;
import com.spring.sys.service.SysResService;
import com.spring.sys.service.SysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统角色管理
 *
 * @author  团子
 * @date 2018/3/1 17:44
 * @since V1.0
 */
@Controller
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysResService sysResService;

    @GetMapping
    String role() {
        return "/sys/role";
    }

    /**
     * 查询所有角色
     *
     * @param params 查询和分页参数
     */
    @SysLog("查询角色")
    @RequiresPermissions("sys:role:list")
    @GetMapping("/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        // 分页
        PageUtils.initPaging(params);
        // 查询数据
        List<SysRoleInfo> list = sysRoleService.queryList(params);
        PageInfo page = new PageInfo(list);
        HashMap dataMap = new HashMap(50);
        dataMap.put("rows", page.getList());
        dataMap.put("total", page.getTotal());
        return Result.datas(dataMap);
    }

    /**
     * 获取角色资源tree
     */
    @GetMapping("/tree")
    @ResponseBody
    public Result tree() {
        Map<String, Object> params = new HashMap<>();
        List<SysResInfo> resList = sysResService.queryList(params);
        for (SysResInfo info : resList) {
            if (info.getResType() == Constants.ResType.CATALOG.getValue()) {
                info.setOpen(true);
            }
        }
        return Result.data("resList", resList);
    }

    /**
     * 新增
     */
    @RequiresPermissions("sys:role:add")
    @GetMapping("/add")
    public String add() {
        return "sys/roleEdit";
    }

    /**
     * 验证是否存在roleName
     */
    @PostMapping("/hasRole")
    @ResponseBody
    boolean hasRole(@RequestParam Map<String, Object> params) {
        return !sysRoleService.hasRole(params);
    }

    /**
     * 根据roleId查询资源列表
     */
    @GetMapping("/roleRes/{id}")
    @ResponseBody
    public Result roleRes(@PathVariable("id") Integer id) {
        List<Integer> resIds = sysRoleService.getResByRId(id);
        return Result.data("resIds", resIds);
    }


    /**
     * 保存角色新增
     */
    @SysLog("新增角色")
    @RequiresPermissions("sys:role:add")
    @PostMapping("/save")
    @ResponseBody
    public Result save(@RequestBody SysRoleInfo role) {
        ValidatorUtils.validate(role, AddGroup.class);
        sysRoleService.save(role);
        return Result.build();
    }

    /**
     * 修改，获取角色信息
     */
    @RequiresPermissions("sys:role:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        SysRoleInfo info = sysRoleService.getRoleByRId(id);
        model.addAttribute("role", info);
        return "sys/roleEdit";
    }

    /**
     * 保存角色修改
     */
    @SysLog("编辑角色")
    @RequiresPermissions("sys:role:edit")
    @PostMapping("/update")
    @ResponseBody
    public Result update(@RequestBody SysRoleInfo role) {
        ValidatorUtils.validate(role, UpdateGroup.class);
        sysRoleService.update(role);
        return Result.build();
    }

    /**
     * 删除角色
     */
    @SysLog("删除角色")
    @RequiresPermissions("sys:role:delete")
    @PostMapping("/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        sysRoleService.delete(ids);
        return Result.build();
    }

    /**
     * 保存权限
     */
    @SysLog("分配权限")
    @RequiresPermissions("sys:role:perm")
    @PostMapping("/savePerms")
    @ResponseBody
    public Result savePerms(@RequestBody SysRoleInfo info) {
        sysRoleService.savePerms(info);
        return Result.build();
    }

}
