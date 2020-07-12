package com.spring.sys.controller;

import com.github.pagehelper.PageInfo;
import com.spring.common.annotation.SysLog;
import com.spring.common.mvc.BaseController;
import com.spring.common.shiro.ShiroUtils;
import com.spring.common.utils.PageUtils;
import com.spring.common.utils.Result;
import com.spring.common.validator.ValidatorUtils;
import com.spring.common.validator.group.AddGroup;
import com.spring.common.validator.group.UpdateGroup;
import com.spring.sys.pojo.SysRoleInfo;
import com.spring.sys.pojo.SysUserInfo;
import com.spring.sys.service.SysUserService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户管理
 *
 * @author  团子
 * @date 2018/3/1 17:44
 * @since V1.0
 */
@Controller
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {
    @Autowired
    private SysUserService sysUserService;

    @GetMapping
    String user() {
        return "/sys/user";
    }

    /**
     * 查询所有用户
     *
     * @param params 查询和分页参数
     */
    @SysLog("查询用户")
    @RequiresPermissions("sys:user:list")
    @GetMapping("/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        // 分页
        PageUtils.initPaging(params);
        // 查询数据
        List<SysUserInfo> list = sysUserService.queryList(params);
        PageInfo page = new PageInfo(list);
        HashMap dataMap = new HashMap(100);
        dataMap.put("rows", page.getList());
        dataMap.put("total", page.getTotal());
        return Result.datas(dataMap);
    }

    /**
     * 新增，获取用户角色列表
     */
    @RequiresPermissions("sys:user:add")
    @GetMapping("/add")
    public String add(Model model) {
        List<SysRoleInfo> roles = sysUserService.queryRolesByUId(null);
        model.addAttribute("roles", roles);
        return "sys/userEdit";
    }

    /**
     * 验证是否存在username
     */
    @PostMapping("/isHasUser")
    @ResponseBody
    boolean isHasUser(@RequestParam Map<String, Object> params) {
        return !sysUserService.isHasUser(params);
    }

    /**
     * 保存新增
     */
    @SysLog("新增用户")
    @RequiresPermissions("sys:user:add")
    @PostMapping("/save")
    @ResponseBody
    public Result save(SysUserInfo user) {
        ValidatorUtils.validate(user, AddGroup.class);
        sysUserService.save(user);
        return Result.build();
    }

    /**
     * 修改，获取用户信息
     */
    @RequiresPermissions("sys:user:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        SysUserInfo uinfo = sysUserService.getUserByUId(id);
        model.addAttribute("user", uinfo);
        // 查询全部角色
        List<SysRoleInfo> roles = sysUserService.queryRolesByUId(null);
        model.addAttribute("roles", roles);
        return "sys/userEdit";
    }

    /**
     * 保存用户修改
     */
    @SysLog("编辑用户")
    @RequiresPermissions("sys:user:edit")
    @PostMapping("/update")
    @ResponseBody
    public Result update(SysUserInfo user) {
        ValidatorUtils.validate(user, UpdateGroup.class);
        sysUserService.update(user);
        return Result.build();
    }

    /**
     * 删除用户
     */
    @SysLog("删除用户")
    @RequiresPermissions("sys:user:delete")
    @PostMapping("/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {

        if(ArrayUtils.contains(ids, 1)){
            return Result.error("系统管理员不能删除!");
        }

        if(ArrayUtils.contains(ids, getUserId())){
            return Result.error("当前用户不能删除!");
        }
        sysUserService.delete(ids);
        return Result.build();
    }

    /**
     * 重置密码
     */
    @SysLog("重置密码")
    @RequiresPermissions("sys:user:resetPwd")
    @PostMapping("/resetPwd")
    @ResponseBody
    public Result resetPwd(@RequestBody Integer[] ids) {
        sysUserService.resetPwd(ids);
        return Result.build();
    }

    /**
     * 验证是否存在密码
     */
    @PostMapping("/hasPwd")
    @ResponseBody
    boolean hasPwd(@RequestParam Map<String, Object> params) {
        return sysUserService.hasPwd(params);
    }

    /**
     * 修改密码
     */
    @GetMapping("/modifyPwd")
    public String modifyPwd() {
        return "sys/pwd";
    }

    /**
     * 保存登录密码
     */
    @SysLog("修改登录密码")
    @PostMapping("/updatePwd")
    @ResponseBody
    public Result updatePwd(@RequestParam String oldPwd, @RequestParam String newPwd) {
        Assert.hasText(oldPwd, "旧密码不为能空");
        Assert.hasText(newPwd, "新密码不为能空");
        //更新密码
        sysUserService.updatePwd(getUserId(),ShiroUtils.sha256(newPwd, getUser().getSalt()));
        return Result.build();
    }

}
