/**
 * AssertType.java
 * Created on  7/4/2016 6:22 PM
 * modify on                user            modify content
 * 7/4/2016 6:22 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.client.ast.enums;

import com.micx.client.ast.AssertContainsImpl;
import com.micx.client.ast.AssertEqualsImpl;
import com.micx.client.ast.AssertScriptImpl;
import com.micx.client.ast.iface.AssertIface;

/**
 * Created by micx  on 2016/04/07 6:22 PM.
 */
public enum AssertType {
    EQUALS(0,new AssertEqualsImpl(), "equals"),
    CONTAINS(1,new AssertContainsImpl(), "contains"),
    SCRIPT(2,new AssertScriptImpl(), "script");

    public final int value;
    public final AssertIface assertResp;
    public final String content;

    AssertType(int value, AssertIface assertResp, String content) {
        this.value = value;
        this.assertResp = assertResp;
        this.content = content;
    }

    public static AssertType parse(int type) {
        for(AssertType assertType: AssertType.values()){
            if (assertType.value == type){
                return assertType;
            }
        }
        return null;
    }
    public static AssertType parse(String content) {
        for(AssertType assertType: AssertType.values()){
            if (assertType.content.equals(content.toLowerCase())){
                return assertType;
            }
        }
        return null;
    }
}
