/**
 * Cmp.java
 * Created on  7/4/2016 4:51 PM
 * modify on                user            modify content
 * 7/4/2016 4:51 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.excel.parse.iface;

import com.micx.excel.entity.ColumnProp;
import com.micx.client.entity.CaseRequest;
import com.micx.excel.enums.ColumnType;

/**
 * Created by micx  on 2016/04/07 4:51 PM.
 */
public interface ColumnParser {
    boolean equeals(ColumnType type, String value);
    void convert(ColumnProp columnProp, String value, CaseRequest caseRequest);
}
