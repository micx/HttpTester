/**
 * TableTD.java
 * Created on  19/4/2016 7:34 PM
 * modify on                user            modify content
 * 19/4/2016 7:34 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.mvc.controller.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Created by micx  on 2016/04/19 7:34 PM.
 */
public class TableTD {
    private int rowSpan;
    private String value;
    private String style;

    public TableTD(String value, String style) {
        this.value = value;
        this.style = style;
    }

    public TableTD() {
    }

    public TableTD(String value) {
        this.value = value;
    }

    public TableTD(int rowSpan, String value) {
        this.rowSpan = rowSpan;
        this.value = value;
    }


    public TableTD(int rowSpan, String value, String style) {
        this.rowSpan = rowSpan;
        this.style = style;
        this.value = value;
    }

    public int getRowSpan() {
        return rowSpan;
    }

    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);

    }
}
