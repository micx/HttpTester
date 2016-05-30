/**
 * RunCaseServiceImpl.java
 * Created on  25/5/2016 3:25 PM
 * modify on                user            modify content
 * 25/5/2016 3:25 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.mvc.service.impl;

import com.micx.mvc.service.iface.ReportService;
import com.micx.mvc.service.iface.RunCaseService;
import com.micx.mvc.service.iface.RunTimeDataService;
import com.micx.mvc.service.task.CaseTaskOfExcel;
import com.micx.threadpool.DefaultThreadPoolImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by micx  on 2016/05/25 3:25 PM.
 */
@Service
public class RunCaseOfExcelServiceImpl implements RunCaseService {
    public void runCase(String taskKey, List<String> fileNameList, String taskFilePath, String taskFileName, ReportService reportService, RunTimeDataService runTimeDataService) {
        CaseTaskOfExcel task = new CaseTaskOfExcel(taskKey, fileNameList, taskFilePath, taskFileName, reportService, runTimeDataService);
        DefaultThreadPoolImpl.submit(task);
    }
}
