/**
 * CaseRequest.java
 * Created on  7/4/2016 4:25 PM
 * modify on                user            modify content
 * 7/4/2016 4:25 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.client.entity;

import com.micx.client.ast.enums.AssertType;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.Map;

/**
 * Created by micx  on 2016/04/07 4:25 PM.
 */
public class CaseRequest {
    private String module;
    private String sheet;
    private String task;
    private String domain;
    private String path;
    private Object expectResult;
    private AssertType assertType;
    private List<ParamPair> params;
    private Map<String, String> comments;
    private List<CookieBO> cookieList;
    private int repeat = 1;
    private int threadNum = 1;
    private int timeOut = 5000;
    private boolean skip = false;
    private HttpMethod httpMethod;

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

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Object getExpectResult() {
        return expectResult;
    }

    public void setExpectResult(Object expectResult) {
        this.expectResult = expectResult;
    }

    public AssertType getAssertType() {
        return assertType;
    }

    public void setAssertType(AssertType assertType) {
        this.assertType = assertType;
    }

    public List<ParamPair> getParams() {
        return params;
    }

    public void setParams(List<ParamPair> params) {
        this.params = params;
    }

    public Map<String, String> getComments() {
        return comments;
    }

    public void setComments(Map<String, String> comments) {
        this.comments = comments;
    }

    public List<CookieBO> getCookieList() {
        return cookieList;
    }

    public void setCookieList(List<CookieBO> cookieList) {
        this.cookieList = cookieList;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);

    }
}
