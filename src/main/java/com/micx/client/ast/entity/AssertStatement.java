/**
 * AssertStatement.java
 * Created on  27/5/2016 9:53 AM
 * modify on                user            modify content
 * 27/5/2016 9:53 AM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.client.ast.entity;

import com.micx.client.ast.enums.AssertType;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by micx  on 2016/05/27 9:53 AM.
 */
public class AssertStatement {
    private AssertType type;
    private String content;
    private AtomicInteger result = new AtomicInteger();

    public AssertStatement() {
    }

    public AssertStatement(AssertType type, String content) {
        this.type = type;
        this.content = content;
    }

    public AssertType getType() {
        return type;
    }

    public void setType(AssertType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AtomicInteger getResult() {
        return result;
    }

    public void setResult(AtomicInteger result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);

    }
}
