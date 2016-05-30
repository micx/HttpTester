/**
 * PageInfo.java
 * Created on  22/4/2016 11:50 AM
 * modify on                user            modify content
 * 22/4/2016 11:50 AM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.mvc.controller.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Created by micx  on 2016/04/22 11:50 AM.
 */
public class PageInfo {
    private String url;
    private String value;
    private int selected;

    public PageInfo() {
    }

    public PageInfo(String url, String value, int selected) {
        this.url = url;
        this.value = value;
        this.selected = selected;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);

    }
}
