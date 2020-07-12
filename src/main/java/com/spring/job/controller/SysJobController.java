package com.spring.job.controller;

import com.github.pagehelper.PageInfo;
import com.spring.common.annotation.SysLog;
import com.spring.common.mvc.BaseController;
import com.spring.common.utils.PageUtils;
import com.spring.common.utils.Result;
import com.spring.common.validator.ValidatorUtils;
import com.spring.job.pojo.SysJobInfo;
import com.spring.job.service.SysJobService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时Job
 *
 * @author  团子
 * @date 2018/4/18 16:21
 * @since V1.0
 */
@Controller
@RequestMapping("/sys/job")
public class SysJobController extends BaseController {
    @Autowired
    private SysJobService sysJobService;

    @GetMapping
    String job() {
        return "/job/job";
    }
    /**
     * 定时Job列表
     */
    @RequiresPermissions("sys:job:list")
    @GetMapping("/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        // 分页
        PageUtils.initPaging(params);
        // 查询数据
        List<SysJobInfo> list = sysJobService.queryList(params);
        PageInfo page = new PageInfo(list);
        HashMap dataMap = new HashMap(50);
        dataMap.put("rows", page.getList());
        dataMap.put("total", page.getTotal());
        return Result.datas(dataMap);
    }

    /**
     * 新增
     */
    @RequiresPermissions("sys:job:add")
    @GetMapping("/add")
    public String add() {
        return "job/jobEdit";
    }

    /**
     * 验证是否存在相同名称
     */
    @PostMapping("/hasJob")
    @ResponseBody
    boolean hasJob(@RequestParam Map<String, Object> params) {
        return !sysJobService.hasJob(params);
    }

    /**
     * 验证cron表达式是否合法
     */
    @PostMapping("/isValidExpression")
    @ResponseBody
    boolean isValidExpression(@RequestParam String cronExp) {
        return  CronExpression.isValidExpression(cronExp);
    }

    /**
     * 保存定时Job
     */
    @SysLog("保存定时Job")
    @RequiresPermissions("sys:job:add")
    @PostMapping("/save")
    @ResponseBody
    public Result save(@RequestBody SysJobInfo sysJob) {
        ValidatorUtils.validate(sysJob);
        sysJobService.save(sysJob);
        return Result.build();
    }

    /**
     * 定时Job信息
     */
    @RequiresPermissions("sys:job:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        SysJobInfo info = sysJobService.getJobByUId(id);
        model.addAttribute("job", info);
        return "job/jobEdit";
    }

    /**
     * 修改定时Job
     */
    @SysLog("修改定时Job")
    @RequiresPermissions("sys:job:edit")
    @PostMapping("/update")
    @ResponseBody
    public Result update(@RequestBody SysJobInfo sysJob) {
        ValidatorUtils.validate(sysJob);
        sysJobService.update(sysJob);
        return Result.build();
    }

    /**
     * 删除定时Job
     */
    @SysLog("删除定时Job")
    @RequiresPermissions("sys:job:delete")
    @PostMapping("/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] jobIds) {
        sysJobService.delete(jobIds);
        return Result.build();
    }

    /**
     * 立即执行Job
     */
    @SysLog("立即执行Job")
    @RequiresPermissions("sys:job:run")
    @RequestMapping("/run")
    @ResponseBody
    public Result runJob(@RequestBody Integer[] jobIds) {
        sysJobService.runJob(jobIds);
        return Result.build();
    }

    /**
     * 暂停定时Job
     */
    @SysLog("暂停定时Job")
    @RequestMapping("/pause")
    @RequiresPermissions("sys:job:pause")
    @ResponseBody
    public Result pauseJob(@RequestBody Integer[] jobIds) {
        sysJobService.pauseJob(jobIds);
        return Result.build();
    }

    /**
     * 恢复定时Job
     */
    @SysLog("恢复定时Job")
    @RequestMapping("/resume")
    @RequiresPermissions("sys:job:resume")
    @ResponseBody
    public Result resumeJob(@RequestBody Integer[] jobIds) {
        sysJobService.resumeJob(jobIds);
        return Result.build();
    }

}
