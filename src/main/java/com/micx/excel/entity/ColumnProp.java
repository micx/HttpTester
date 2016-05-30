/**
 * CaseKey.java
 * Created on  7/4/2016 4:35 PM
 * modify on                user            modify content
 * 7/4/2016 4:35 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.excel.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Created by micx  on 2016/04/07 4:35 PM.
 */
public class ColumnProp {
    private int type;
    private String name;

    public ColumnProp(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);

    }
}
