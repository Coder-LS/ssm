package com.itheima.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.ssm.domain.SysLog;
import com.itheima.ssm.service.ISysLogService;
import org.apache.log4j.net.SyslogAppender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/sysLog")
public class SysLogController {

    @Autowired
    private ISysLogService sysLogService;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll(
            @RequestParam(
                    name = "page",
                    required = true,
                    defaultValue="1") Integer page,
            @RequestParam(
                    name="size",
                    required = true,
                    defaultValue = "5")
                    Integer size
    ) throws Exception {
        ModelAndView mv=new ModelAndView();
       List<SysLog> sysLogList= sysLogService.findAll(page,size);
        PageInfo pageInfo = new PageInfo(sysLogList);
        //PageInfo就是一个分页的Bean
        mv.addObject("pageInfo",pageInfo);
       mv.setViewName("syslog-list");
        return mv;
    }


    //按照挑选的日志进行删除操作
    @RequestMapping("/deleteSysLog.do")
    public String deleteRole(@RequestParam(name="ids",required = true) String[] sysLogIds) throws Exception {
        sysLogService.deleteSysLogById(sysLogIds);
        return "redirect:findAll.do";
    }

}
