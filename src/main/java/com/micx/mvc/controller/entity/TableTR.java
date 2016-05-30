/**
 * TableTR.java
 * Created on  19/4/2016 7:35 PM
 * modify on                user            modify content
 * 19/4/2016 7:35 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.mvc.controller.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.List;

/**
 * Created by micx  on 2016/04/19 7:35 PM.
 */
public class TableTR {
    List<TableTD> tdList;

    public List<TableTD> getTdList() {
        return tdList;
    }

    public void setTdList(List<TableTD> tdList) {
        this.tdList = tdList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);

    }
}
