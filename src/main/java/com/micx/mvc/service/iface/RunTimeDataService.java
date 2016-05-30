/**
 * RunTimeDataService.java
 * Created on  14/4/2016 3:45 PM
 * modify on                user            modify content
 * 14/4/2016 3:45 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.mvc.service.iface;

import com.micx.data.ProcessInfo;

/**
 * Created by micx  on 2016/04/14 3:45 PM.
 */
public interface RunTimeDataService {
    void initTaskInfoData(String taskKey, int totalCnt);
    void increTaskCnt(String taskKey, int cnt);
    ProcessInfo getTaskRunTimeData(String taskKey);
}
