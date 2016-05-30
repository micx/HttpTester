/**
 * RunTimeDataServiceImpl.java
 * Created on  14/4/2016 3:46 PM
 * modify on                user            modify content
 * 14/4/2016 3:46 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.mvc.service.impl;

import com.micx.data.ProcessInfo;
import com.micx.data.TaskInfoData;
import com.micx.mvc.service.iface.RunTimeDataService;
import org.springframework.stereotype.Service;

/**
 * Created by micx  on 2016/04/14 3:46 PM.
 */
@Service
public class RunTimeDataServiceImpl implements RunTimeDataService {

    public void initTaskInfoData(String taskKey, int totalCnt) {
        TaskInfoData.initTask(taskKey, totalCnt);
    }

    public void increTaskCnt(String taskKey, int cnt) {
        TaskInfoData.increTaskCnt(taskKey, cnt);
    }

    public ProcessInfo getTaskRunTimeData(String taskKey) {
        return TaskInfoData.map.get(taskKey);
    }
}
