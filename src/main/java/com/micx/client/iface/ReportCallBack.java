package com.micx.client.iface; /**
 * com.micx.client.iface.ReportCallBack.java
 * Created on  1/4/2016 12:25 PM
 * modify on                user            modify content
 * 1/4/2016 12:25 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

import com.micx.client.ast.entity.AssertFailedMsg;
import com.micx.client.entity.TestResult;

import java.util.List;

/**
 * Created by micx  on 2016/04/01 12:25 PM.
 */
public interface ReportCallBack {
    TestResult generateReport(int sucCnt, int failedCnt, long totalCost, long avg);
    void handleResponse(int status, String response);
    boolean assertResponse(int status, String response, List<AssertFailedMsg> errMsgList);
}
