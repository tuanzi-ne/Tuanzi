package com.spring.job.controller;

import com.github.pagehelper.PageInfo;
import com.spring.common.utils.PageUtils;
import com.spring.common.utils.Result;
import com.spring.job.pojo.SysJobLogInfo;
import com.spring.job.service.SysJobLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时Job日志
 * @author  团子
 * @since V1.0
 * @date 2018/4/18 17:19
 */
@Controller
@RequestMapping("/sys/joblog")
public class SysJobLogController {
	@Autowired
	private SysJobLogService sysJobLogService;

	@GetMapping
	String joblog() {
		return "/job/joblog";
	}
	/**
	 * 定时任务日志列表
	 */
	@RequiresPermissions("sys:joblog:list")
	@GetMapping("/list")
    @ResponseBody
	public Result list(@RequestParam Map<String, Object> params){
        // 分页
        PageUtils.initPaging(params);
        // 查询数据
        List<SysJobLogInfo> list = sysJobLogService.queryList(params);
        PageInfo page = new PageInfo(list);
        HashMap dataMap = new HashMap(50);
        dataMap.put("rows", page.getList());
        dataMap.put("total", page.getTotal());
        return Result.datas(dataMap);
	}

}
