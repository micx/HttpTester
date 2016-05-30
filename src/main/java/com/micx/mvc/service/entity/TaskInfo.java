/**
 * TaskInfo.java
 * Created on  13/4/2016 12:18 PM
 * modify on                user            modify content
 * 13/4/2016 12:18 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.mvc.service.entity;

import com.micx.client.entity.TestResult;
import com.micx.mvc.service.enums.TaskStatus;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

/**
 * Created by micx  on 2016/04/13 12:18 PM.
 */
public class TaskInfo implements Serializable{
    private TaskStatus status;
    private List<TestResult> resultList;

    public TaskInfo() {
    }

    public TaskInfo(TaskStatus status, List<TestResult> resultList) {
        this.status = status;
        this.resultList = resultList;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public List<TestResult> getResultList() {
        return resultList;
    }

    public void setResultList(List<TestResult> resultList) {
        this.resultList = resultList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);

    }
}
