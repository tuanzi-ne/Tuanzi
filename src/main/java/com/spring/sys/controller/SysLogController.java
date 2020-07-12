package com.spring.sys.controller;

import com.github.pagehelper.PageInfo;
import com.spring.common.utils.PageUtils;
import com.spring.common.utils.Result;
import com.spring.sys.pojo.SysLogInfo;
import com.spring.sys.service.SysLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 系统日志
 *
 * @author  团子
 * @date 2018/4/10 19:53
 * @since V1.0
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController {
    @Autowired
    private SysLogService sysLogService;

    @GetMapping
    String log() {
        return "/sys/log";
    }

    /**
     * 列表
     */
    @RequiresPermissions("sys:log:list")
    @ResponseBody
    @GetMapping("/list")
    public Result list(@RequestParam Map<String, Object> params) {
        // 分页
        PageUtils.initPaging(params);
        // 查询数据
        List<SysLogInfo> list = sysLogService.queryList(params);
        PageInfo page = new PageInfo(list);
        HashMap dataMap = new HashMap(50);
        dataMap.put("rows", page.getList());
        dataMap.put("total", page.getTotal());
        return Result.datas(dataMap);
    }
}
