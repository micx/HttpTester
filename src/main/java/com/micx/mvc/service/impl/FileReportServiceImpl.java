/**
 * ReportServiceImpl.java
 * Created on  13/4/2016 12:13 PM
 * modify on                user            modify content
 * 13/4/2016 12:13 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.mvc.service.impl;

import com.micx.excel.file.FileUtil;
import com.micx.mvc.service.entity.TaskInfo;
import com.micx.mvc.service.iface.ReportService;
import com.micx.utils.JsonUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by micx  on 2016/04/13 12:13 PM.
 */
@Service
public class FileReportServiceImpl implements ReportService {

    public void addTaskInfo(String path, String fileName, TaskInfo taskInfo) {
        try {
            FileUtil.writeFile(path, fileName, JsonUtil.parseJson(taskInfo));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public TaskInfo getTaskInfo(String path,String fileName) {
        try {
            String taskJson = FileUtil.readFile(path+fileName);
            TaskInfo taskInfo = JsonUtil.parseObject(taskJson, TaskInfo.class);
            return taskInfo;
        } catch (IOException e) {
            System.out.println(e);
        }

        return null;
    }
}
