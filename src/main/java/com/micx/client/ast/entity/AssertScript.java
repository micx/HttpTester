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

import com.beust.jcommander.internal.Lists;
import com.micx.client.ast.enums.AssertLogic;
import com.micx.client.ast.enums.AssertType;
import com.micx.utils.JsonUtil;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.IOException;
import java.util.List;

/**
 * Created by micx  on 2016/05/27 9:52 AM.
 */
public class AssertScript {
    private List<AssertGroup> assertGroups;

    public List<AssertGroup> getAssertGroups() {
        return assertGroups;
    }

    public void setAssertGroups(List<AssertGroup> assertGroups) {
        this.assertGroups = assertGroups;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);

    }

    public static void main(String[] args) {
        AssertScript script = new AssertScript();
        List<AssertGroup> assertGroups = Lists.newArrayList();
        script.setAssertGroups(assertGroups);

        AssertGroup group = new AssertGroup();
        group.setGroupName("group - 1");
        group.setStatements(generateStatements1());
        group.setAssertLogic(AssertLogic.AND);
        assertGroups.add(group);
        AssertGroup group1 = new AssertGroup();
        group1.setGroupName("group - 2");
        group1.setStatements(generateStatements2());
        group1.setAssertLogic(AssertLogic.OR);
        assertGroups.add(group1);

        String json = JsonUtil.parseJson(script);
        System.out.println(json);
        String assertJson = "{\n" +
                "    \"assertGroups\":[\n" +
                "        {\n" +
                "            \"groupName\":\"group - 1\",\n" +
                "            \"assertLogic\":\"AND\",\n" +
                "            \"statements\":[\n" +
                "                {\n" +
                "                    \"type\":\"CONTAINS\",\n" +
                "                    \"content\":\"hello\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"type\":\"CONTAINS\",\n" +
                "                    \"content\":\"world\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"groupName\":\"group - 2\",\n" +
                "            \"assertLogic\":\"OR\",\n" +
                "            \"statements\":[\n" +
                "                {\n" +
                "                    \"type\":\"EQUALS\",\n" +
                "                    \"content\":\"hello\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"type\":\"EQUALS\",\n" +
                "                    \"content\":\"world\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        try {
            AssertScript script1 = JsonUtil.parseObject(assertJson, AssertScript.class);
            System.out.println(script1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<AssertStatement> generateStatements1() {
        List<AssertStatement> list = Lists.newArrayList();
        list.add( new AssertStatement(AssertType.CONTAINS, "hello"));
        list.add( new AssertStatement(AssertType.CONTAINS, "world"));
        return list;
    }

    private static List<AssertStatement> generateStatements2() {
        List<AssertStatement> list = Lists.newArrayList();
        list.add( new AssertStatement(AssertType.EQUALS, "hello"));
        list.add( new AssertStatement(AssertType.EQUALS, "world"));
        return list;
    }
}
