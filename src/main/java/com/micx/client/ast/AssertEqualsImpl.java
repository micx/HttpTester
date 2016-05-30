/**
 * AssertEqualsImpl.java
 * Created on  7/4/2016 7:37 PM
 * modify on                user            modify content
 * 7/4/2016 7:37 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.client.ast;

import com.micx.client.ast.entity.AssertFailedMsg;
import com.micx.client.ast.enums.AssertType;
import com.micx.client.ast.iface.AssertIface;

import java.util.List;

/**
 * Created by micx  on 2016/04/07 7:37 PM.
 */
public class AssertEqualsImpl implements AssertIface<String> {

    private AssertFailedMsg assertFailedMsg;

    public boolean assertResponse(String expected, String actual, List<AssertFailedMsg> astResults) {
        boolean result = actual == null ? false : actual.toLowerCase().equals(expected.toLowerCase());
        if (!result){
            AssertFailedMsg assertFailedMsg =  new AssertFailedMsg(AssertType.EQUALS, expected, actual, result);
            astResults.add(assertFailedMsg);
        }
        return result;
    }
}
