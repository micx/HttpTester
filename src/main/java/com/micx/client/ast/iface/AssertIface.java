/**
 * AssertIface.java
 * Created on  7/4/2016 7:37 PM
 * modify on                user            modify content
 * 7/4/2016 7:37 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.client.ast.iface;

import com.micx.client.ast.entity.AssertFailedMsg;

import java.util.List;

/**
 * Created by micx  on 2016/04/07 7:37 PM.
 */
public interface AssertIface<T> {
    boolean assertResponse(T expected, String actual, List<AssertFailedMsg> errMsgList);
}
