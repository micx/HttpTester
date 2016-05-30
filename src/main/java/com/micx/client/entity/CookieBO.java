/**
 * Cookie.java
 * Created on  1/4/2016 9:52 AM
 * modify on                user            modify content
 * 1/4/2016 9:52 AM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.client.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Created by micx  on 2016/04/01 9:52 AM.
 */
public class CookieBO {
    private String domain;
    private long expirationDate;
    private boolean hostOnly;
    private boolean httpOnly;
    private String name;
    private String path;
    private boolean secure;
    private boolean session;
    private String storeId;
    private String value;
    private int id;

    public CookieBO() {
    }

    public CookieBO(String domain, long expirationDate, String name, String path, String value) {
        this.domain = domain;
        this.expirationDate = expirationDate;
        this.name = name;
        this.path = path;
        this.value = value;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public long getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(long expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isHostOnly() {
        return hostOnly;
    }

    public void setHostOnly(boolean hostOnly) {
        this.hostOnly = hostOnly;
    }

    public boolean isHttpOnly() {
        return httpOnly;
    }

    public void setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public boolean isSession() {
        return session;
    }

    public void setSession(boolean session) {
        this.session = session;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
