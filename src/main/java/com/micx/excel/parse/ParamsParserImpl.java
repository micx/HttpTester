/**
 * CmpContains.java
 * Created on  7/4/2016 4:51 PM
 * modify on                user            modify content
 * 7/4/2016 4:51 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.excel.parse;

import com.beust.jcommander.internal.Lists;
import com.beust.jcommander.internal.Maps;
import com.micx.client.entity.CaseRequest;
import com.micx.client.entity.ParamPair;
import com.micx.excel.entity.ColumnProp;
import com.micx.excel.enums.ColumnType;
import com.micx.excel.parse.iface.ColumnParser;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

import static com.micx.excel.enums.ColumnType.parse;

/**
 * Created by micx  on 2016/04/07 4:51 PM.
 */
public class ParamsParserImpl implements ColumnParser {

    public boolean equeals(ColumnType type, String value) {
        return value.startsWith(type.content);
    }

    public void convert(ColumnProp columnProp, String value, CaseRequest caseRequest) {
        ColumnType type = parse(columnProp.getType());
        if ("$EMP".equals(value)){
            return;
        }
        switch (type){
            case COMMENT:
                Map<String, String> params = caseRequest.getComments();
                if (params == null){
                    params = Maps.newHashMap();
                    caseRequest.setComments(params);
                }
                params.put(columnProp.getName().replace(type.content, StringUtils.EMPTY), value);
                break;
            case PARAMS:
                List<ParamPair> paramList = caseRequest.getParams();
                if (paramList == null){
                    paramList = Lists.newArrayList();
                    caseRequest.setParams(paramList);
                }
                paramList.add(new ParamPair(columnProp.getName().replace(type.content, StringUtils.EMPTY), value));
                break;
        }


    }
}
