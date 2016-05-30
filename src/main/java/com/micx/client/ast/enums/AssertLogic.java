/**
 * AssertLogic.java
 * Created on  27/5/2016 9:56 AM
 * modify on                user            modify content
 * 27/5/2016 9:56 AM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.client.ast.enums;

/**
 * Created by micx  on 2016/05/27 9:56 AM.
 */
public enum  AssertLogic {
    OR(0, "or"),
    AND(1, "and");

    public final int value;
    public final String content;

    AssertLogic(int value, String content) {
        this.value = value;
        this.content = content;
    }

    public static AssertLogic parse(int type) {
        for(AssertLogic assertType: AssertLogic.values()){
            if (assertType.value == type){
                return assertType;
            }
        }
        return null;
    }
}
