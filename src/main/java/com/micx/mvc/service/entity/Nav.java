/**
 * Nav.java
 * Created on  12/4/2016 10:55 PM
 * modify on                user            modify content
 * 12/4/2016 10:55 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.mvc.service.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

/**
 * Created by micx  on 2016/04/12 10:55 PM.
 */
public class Nav implements Serializable{
    private int id;
    private String name;
    private int selected;

    public Nav() {
    }

    public Nav(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
