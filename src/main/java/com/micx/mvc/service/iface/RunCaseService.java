/**
 * TestCaseService.java
 * Created on  25/5/2016 3:23 PM
 * modify on                user            modify content
 * 25/5/2016 3:23 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.mvc.service.iface;

import java.util.List;

/**
 * Created by micx  on 2016/05/25 3:23 PM.
 */
public interface RunCaseService {
    void runCase(String taskKey, List<String> fileNameList, String taskFilePath, String taskFileName,
                 ReportService reportService, RunTimeDataService runTimeDataService);
}
