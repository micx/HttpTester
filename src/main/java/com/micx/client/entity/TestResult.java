/**
 * Report.java
 * Created on  1/4/2016 12:23 PM
 * modify on                user            modify content
 * 1/4/2016 12:23 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.client.entity;

import com.beust.jcommander.internal.Maps;
import com.micx.client.ast.entity.AssertFailedMsg;
import com.micx.utils.JsonUtil;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by micx  on 2016/04/01 12:23 PM.
 */
public class TestResult implements Serializable{

    private String module;
    private String sheet;
    private String task;
    private int successCnt;
    private int failedCnt;
    private long totalCost;
    private long serverAvgCost;
    private long serverQPS;
    private long clientAvgCost;
    private long cost;
    private boolean skip;
    private Map<String, AtomicInteger> errMsg = Maps.newHashMap();

    public TestResult() {
    }

    public TestResult(String task, int successCnt, int failedCnt, long totalCost, long serverAvgCost) {
        this.task = task;
        this.successCnt = successCnt;
        this.failedCnt = failedCnt;
        this.totalCost = totalCost;
        this.serverAvgCost = serverAvgCost;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getSheet() {
        return sheet;
    }

    public void setSheet(String sheet) {
        this.sheet = sheet;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getSuccessCnt() {
        return successCnt;
    }

    public void setSuccessCnt(int successCnt) {
        this.successCnt = successCnt;
    }

    public int getFailedCnt() {
        return failedCnt;
    }

    public void setFailedCnt(int failedCnt) {
        this.failedCnt = failedCnt;
    }

    public long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(long totalCost) {
        this.totalCost = totalCost;
    }

    public long getServerAvgCost() {
        return serverAvgCost;
    }

    public void setServerAvgCost(long serverAvgCost) {
        this.serverAvgCost = serverAvgCost;
    }

    public long getClientAvgCost() {
        return clientAvgCost;
    }

    public void setClientAvgCost(long clientAvgCost) {
        this.clientAvgCost = clientAvgCost;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public long getServerQPS() {
        return serverQPS;
    }

    public void setServerQPS(long serverQPS) {
        this.serverQPS = serverQPS;
    }

    public void addErrMsg(Map<String, AtomicInteger> errMsg) {
        Set<Map.Entry<String, AtomicInteger>> entries = errMsg.entrySet();
        for (Map.Entry<String, AtomicInteger> entry: entries) {
            this.addErrMsg(entry.getKey(), entry.getValue());
        }
    }

    public void addErrMsg(String errMsg) {
        this.addErrMsg(errMsg, new AtomicInteger(1));
    }

    public void addErrMsg(List<AssertFailedMsg> errMsg) {
        for (AssertFailedMsg msg: errMsg) {
            this.addErrMsg(JsonUtil.parseJson(msg), new AtomicInteger(1));
        }
    }

    public void addErrMsg(String errMsg, AtomicInteger cnt) {
        AtomicInteger totalCnt = this.errMsg.get(errMsg);
        if (totalCnt == null){
            synchronized (this) {
                if (totalCnt == null) {
                    totalCnt = new AtomicInteger(0);
                }
            }
        }
        totalCnt.addAndGet(cnt.intValue());
        this.errMsg.put(errMsg, totalCnt);
    }

    public Map<String, AtomicInteger> getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(Map<String, AtomicInteger> errMsg) {
        this.errMsg = errMsg;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);

    }
}
