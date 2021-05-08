package com.itheima.ssm.service;

import com.itheima.ssm.domain.SysLog;

import java.util.List;

public interface ISysLogService {

    public void save(SysLog sysLog) throws Exception;

    List<SysLog> findAll(int page,int size) throws Exception;

    void deleteSysLogById(String[] sysLogIds) throws Exception;
}
