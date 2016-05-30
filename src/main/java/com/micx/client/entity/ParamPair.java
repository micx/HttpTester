/**
 * ParamPair.java
 * Created on  27/4/2016 10:22 PM
 * modify on                user            modify content
 * 27/4/2016 10:22 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.client.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Created by micx  on 2016/04/27 10:22 PM.
 */
public class ParamPair {
    private String key;
    private String value;

    public ParamPair() {
    }

    public ParamPair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);

    }
}
