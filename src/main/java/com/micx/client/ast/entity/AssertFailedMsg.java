/**
 * AssertFailedMsg.java
 * Created on  10/4/2016 9:42 AM
 * modify on                user            modify content
 * 10/4/2016 9:42 AM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.client.ast.entity;

import com.micx.client.ast.enums.AssertType;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

/**
 * Created by micx  on 2016/04/10 9:42 AM.
 */
public class AssertFailedMsg implements Serializable{
    private AssertType assertType;
    private Object expect;
    private Object actual;
    private boolean assertResult;

    public AssertFailedMsg() {
    }

    public AssertFailedMsg(AssertType assertType, Object expect, Object actual, boolean assertResult) {
        this.assertType = assertType;
        this.expect = expect;
        this.actual = actual;
        this.assertResult = assertResult;
    }

    public AssertType getAssertType() {
        return assertType;
    }

    public void setAssertType(AssertType assertType) {
        this.assertType = assertType;
    }

    public Object getExpect() {
        return expect;
    }

    public void setExpect(Object expect) {
        this.expect = expect;
    }

    public Object getActual() {
        return actual;
    }

    public void setActual(Object actual) {
        this.actual = actual;
    }

    public boolean isAssertResult() {
        return assertResult;
    }

    public void setAssertResult(boolean assertResult) {
        this.assertResult = assertResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AssertFailedMsg)) return false;

        AssertFailedMsg that = (AssertFailedMsg) o;

        if (assertResult != that.assertResult) return false;
        if (assertType != that.assertType) return false;
        if (expect != null ? !expect.equals(that.expect) : that.expect != null) return false;
        return !(actual != null ? !actual.equals(that.actual) : that.actual != null);

    }

    @Override
    public int hashCode() {
        int result = assertType != null ? assertType.hashCode() : 0;
        result = 31 * result + (expect != null ? expect.hashCode() : 0);
        result = 31 * result + (actual != null ? actual.hashCode() : 0);
        result = 31 * result + (assertResult ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);

    }
}
