/**
 * AssertScript.java
 * Created on  27/5/2016 9:52 AM
 * modify on                user            modify content
 * 27/5/2016 9:52 AM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.client.ast.entity;

import com.micx.client.ast.enums.AssertLogic;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by micx  on 2016/05/27 9:52 AM.
 */
public class AssertGroup {
    private String groupName;
    private AssertLogic assertLogic;
    private List<AssertStatement> statements;
    private AtomicInteger result = new AtomicInteger();

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<AssertStatement> getStatements() {
        return statements;
    }

    public void setStatements(List<AssertStatement> statements) {
        this.statements = statements;
    }

    public AtomicInteger getResult() {
        return result;
    }

    public void setResult(AtomicInteger result) {
        this.result = result;
    }

    public AssertLogic getAssertLogic() {
        return assertLogic;
    }

    public void setAssertLogic(AssertLogic assertLogic) {
        this.assertLogic = assertLogic;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);

    }
}
