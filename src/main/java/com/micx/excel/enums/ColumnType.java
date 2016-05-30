/**
 * KeyType.java
 * Created on  7/4/2016 4:37 PM
 * modify on                user            modify content
 * 7/4/2016 4:37 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.excel.enums;

import com.micx.excel.parse.iface.ColumnParser;
import com.micx.excel.parse.BaseParserImpl;
import com.micx.excel.parse.ParamsParserImpl;

/**
 * Created by micx  on 2016/04/07 4:37 PM.
 */
public enum ColumnType {
    ID(0,new BaseParserImpl(),"id"),
    DOMAIN(1,new BaseParserImpl(),"domain"),
    PATH(2,new BaseParserImpl(),"path"),
    PARAMS(3,new ParamsParserImpl(),"$"),
    COMMENT(4,new ParamsParserImpl(),"#"),
    EXPECT_RESULT(5,new BaseParserImpl(),"expectresult"),
    ASSERT_TYPE(6,new BaseParserImpl(),"asserttype"),
    HTTP_METHOD(7,new BaseParserImpl(),"httpmethod"),
    COOKIES(8,new BaseParserImpl(),"cookies"),
    REPEAT(9,new BaseParserImpl(),"repeat"),
    THREADNUM(10,new BaseParserImpl(),"threadnum"),
    TIMEOUT(11,new BaseParserImpl(),"timeout"),
    SKIP_CASE(12,new BaseParserImpl(),"skip"),
    ;

    public final int value;
    public final String content;
    public final ColumnParser ColumnParser;

    ColumnType(int value, ColumnParser ColumnParser, String content) {
        this.value = value;
        this.content = content;
        this.ColumnParser = ColumnParser;
    }

    public static ColumnType parse(int type) {
        for(ColumnType columnType : ColumnType.values()){
            if (columnType.value == type){
                return columnType;
            }
        }
        return null;
    }
}
