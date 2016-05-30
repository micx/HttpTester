/**
 * CaseReportService.java
 * Created on  13/4/2016 12:12 PM
 * modify on                user            modify content
 * 13/4/2016 12:12 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.mvc.service.iface;

import com.micx.mvc.service.entity.TaskInfo;

/**
 * Created by micx  on 2016/04/13 12:12 PM.
 */
public interface ReportService {

    void addTaskInfo(String path, String fileName, TaskInfo taskInfo);

    TaskInfo getTaskInfo(String path,String fileName);
}
