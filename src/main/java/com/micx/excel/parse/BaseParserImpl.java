/**
 * CmpEquals.java
 * Created on  7/4/2016 4:52 PM
 * modify on                user            modify content
 * 7/4/2016 4:52 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.excel.parse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.micx.client.entity.CookieBO;
import com.micx.excel.parse.iface.ColumnParser;
import com.micx.client.ast.enums.AssertType;
import com.micx.excel.entity.ColumnProp;
import com.micx.client.entity.CaseRequest;
import com.micx.excel.enums.ColumnType;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import com.micx.utils.JsonUtil;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.util.List;

/**
 * Created by micx  on 2016/04/07 4:52 PM.
 */
public class BaseParserImpl implements ColumnParser {

    public boolean equeals(ColumnType type, String value) {
        return type.content.equals(value.toLowerCase());
    }

    public void convert(ColumnProp columnProp, String value, CaseRequest caseRequest) {
        if (columnProp == null){
            return;
        }
        ColumnType type = ColumnType.parse(columnProp.getType());
        switch (type){
            case ID:
                caseRequest.setTask(value);
                break;
            case ASSERT_TYPE:
                caseRequest.setAssertType(AssertType.parse(value));
                break;
            case DOMAIN:
                caseRequest.setDomain(value);
                break;
            case PATH:
                caseRequest.setPath(value);
                break;
            case EXPECT_RESULT:
                caseRequest.setExpectResult(value);
                break;
            case COOKIES:
                caseRequest.setCookieList(parseCookies(value));
                break;
            case REPEAT:
                int repeat = NumberUtils.toInt(value);
                caseRequest.setRepeat(repeat > 0 ? repeat : 1);
                break;
            case THREADNUM:
                int threadNum = NumberUtils.toInt(value);
                caseRequest.setThreadNum(threadNum > 0 ? threadNum : 1);
                break;
            case TIMEOUT:
                int timeOut = NumberUtils.toInt(value);
                caseRequest.setTimeOut(timeOut > 0 ? timeOut : 1);
                break;
            case SKIP_CASE:
                boolean skip = BooleanUtils.toBoolean(value);
                caseRequest.setSkip(skip);
                break;
            case HTTP_METHOD:
                caseRequest.setHttpMethod(parseHttpMethod(value));
                break;
        }
    }

    private HttpMethod parseHttpMethod(String value) {
        if (StringUtils.isBlank(value)){
            return HttpMethod.POST;
        }
        if ("get".equals(value.toLowerCase())){
            return HttpMethod.GET;
        }else if ("post".equals(value.toLowerCase())){
            return HttpMethod.POST;
        }
        return HttpMethod.POST;
    }

    private List<CookieBO> parseCookies(String cookieJson) {
        if (StringUtils.isBlank(cookieJson)) {
            return ListUtils.EMPTY_LIST;
        }
        try {
            List<CookieBO> cookieList = JsonUtil.parseObject(cookieJson, new TypeReference<List<CookieBO>>() {
            });
            if (cookieList == null){
                return ListUtils.EMPTY_LIST;
            }
            return cookieList;
        } catch (IOException e) {
            System.out.println(e);
        }
        return ListUtils.EMPTY_LIST;
    }
}
