/**
 * ProcessInfo.java
 * Created on  14/4/2016 1:07 PM
 * modify on                user            modify content
 * 14/4/2016 1:07 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.data;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by micx  on 2016/04/14 1:07 PM.
 */
public class ProcessInfo {
    private AtomicInteger curCnt = new AtomicInteger(0);
    private AtomicInteger totalCnt= new AtomicInteger(Integer.MAX_VALUE);

    public ProcessInfo() {
    }

    public ProcessInfo(AtomicInteger curCnt, AtomicInteger totalCnt) {
        this.curCnt = curCnt;
        this.totalCnt = totalCnt;
    }

    public AtomicInteger getCurCnt() {
        return curCnt;
    }

    public void setCurCnt(AtomicInteger curCnt) {
        this.curCnt = curCnt;
    }

    public AtomicInteger getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(AtomicInteger totalCnt) {
        this.totalCnt = totalCnt;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);

    }
}
