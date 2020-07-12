package com.spring.sys.controller;

import com.spring.common.annotation.SysLog;
import com.spring.common.mvc.BaseController;
import com.spring.common.utils.Constants;
import com.spring.common.utils.PageUtils;
import com.spring.common.utils.Result;
import com.spring.common.validator.ValidatorUtils;
import com.spring.common.validator.group.AddGroup;
import com.spring.common.validator.group.UpdateGroup;
import com.spring.sys.pojo.SysDeptInfo;
import com.spring.sys.service.SysDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 系统部门管理
 *
 * @author  团子
 * @date 2018/3/1 17:44
 * @since V1.0
 */
@Controller
@RequestMapping("/sys/dept")
public class SysDeptController extends BaseController {
    @Autowired
    private SysDeptService sysDeptService;

    @GetMapping
    String dept() {
        return "/sys/dept";
    }
    /**
     * 查询所有部门
     *
     * @param params 查询参数
     */
    @SysLog("查询部门")
    @RequiresPermissions("sys:dept:list")
    @GetMapping("/list")
    @ResponseBody
    public List<SysDeptInfo> list(@RequestParam Map<String, Object> params) {
        // 查询数据
        List<SysDeptInfo> list = sysDeptService.queryList(PageUtils.filterParams(params));
        return list;
    }

    /**
     * 新增
     */
    @RequiresPermissions("sys:dept:add")
    @GetMapping("/add")
    public String add() {
        return "sys/deptEdit";
    }

    /**
     * 验证是否存在部门
     */
    @PostMapping("/hasDept")
    @ResponseBody
    boolean hasDept(@RequestParam Map<String, Object> params) {
        return !sysDeptService.hasDept(params);
    }

    /**
     * 获取部门列表
     */
    @GetMapping("/tree")
    @ResponseBody
    public Result tree() {
        List<SysDeptInfo> deptList = sysDeptService.queryList(null);
        //添加顶级菜资源
        SysDeptInfo root = new SysDeptInfo();
        root.setId(Constants.RES_TOP_ID);
        root.setDeptName("顶级部门");
        root.setParentId(-1);
        root.setOpen(true);
        deptList.add(root);
        return Result.data("deptList", deptList);
    }

    /**
     * 根据deptId获取部门列表
     * 新增时获取全部列表，更新时获取除本部门及其子部门以外的部门列表
     */
    @PostMapping("/select")
    @ResponseBody
    public Result select(@RequestParam String deptId) {
        List<SysDeptInfo> deptList = sysDeptService.selectDeptById(deptId);
        return Result.data("deptList", deptList);
    }

    /**
     * 保存新增
     */
    @SysLog("新增部门")
    @RequiresPermissions("sys:dept:add")
    @PostMapping("/save")
    @ResponseBody
    public Result save(@RequestBody SysDeptInfo dept) {
        ValidatorUtils.validate(dept, AddGroup.class);
        sysDeptService.save(dept);
        return Result.build();
    }

    /**
     * 修改，获取部门信息
     */
    @RequiresPermissions("sys:dept:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        SysDeptInfo uinfo = sysDeptService.getDeptById(id);
        model.addAttribute("dept", uinfo);
        return "sys/deptEdit";
    }

    /**
     * 保存部门修改
     */
    @SysLog("编辑部门")
    @RequiresPermissions("sys:dept:edit")
    @PostMapping("/update")
    @ResponseBody
    public Result update(@RequestBody SysDeptInfo dept) {
        ValidatorUtils.validate(dept, UpdateGroup.class);
        sysDeptService.update(dept);
        return Result.build();
    }

    /**
     * 删除部门
     */
    @SysLog("删除部门")
    @RequiresPermissions("sys:dept:delete")
    @PostMapping("/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        //判断是否包含子部门
        Map<String, Object> params = new HashMap<>(15);
        for (Integer deptId:ids){
            params.put("parentId", deptId);
            List<SysDeptInfo> deptList = sysDeptService.queryList(params);
            if (deptList.size() > 0) {
                return Result.error("请先删除子部门!");
            }
        }
        sysDeptService.delete(ids);
        return Result.build();
    }
}
