/**
 * TableUtil.java
 * Created on  22/4/2016 11:08 AM
 * modify on                user            modify content
 * 22/4/2016 11:08 AM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.mvc.controller.util;

import com.beust.jcommander.internal.Lists;
import com.beust.jcommander.internal.Maps;
import com.micx.client.entity.TestResult;
import com.micx.mvc.controller.entity.TableTD;
import com.micx.mvc.controller.entity.TableTR;
import com.micx.mvc.service.entity.TaskInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by micx  on 2016/04/22 11:08 AM.
 */
public class TableUtil {
    public static List<TableTR> parseTable(String taskKey, TaskInfo taskInfo) {
        if (taskInfo == null){
            return ListUtils.EMPTY_LIST;
        }
        Map<String,Map<String,Map<String,TestResult>>> moduleMap = genTable(taskInfo);
        Set<String> moduleKeySet = moduleMap.keySet();
        List<TableTR> tableList = Lists.newArrayList();

        for (String moduleKey: moduleKeySet){
            Map<String, Map<String, TestResult>> sheetMap = moduleMap.get(moduleKey);
            Set<String> sheetKeySet = sheetMap.keySet();
            int rowSpan1 = 0;
            List<TableTD> moduleTDList = null;
            for (String sheetKey: sheetKeySet){
                Map<String, TestResult> sceneMap = sheetMap.get(sheetKey);
                Set<String> sceneKeySet = sceneMap.keySet();
                List<TableTR> tmpList = Lists.newArrayList();
                int rowSpan2 = 0;
                int passedCase = 0;
                int skipCase = 0;
                for (String sceneKey: sceneKeySet){
                    TableTR tr = new TableTR();
                    List<TableTD> tdList = Lists.newArrayList();
                    TestResult testResult = sceneMap.get(sceneKey);
                    boolean skip = testResult.isSkip();
                    int failCnt = testResult.getFailedCnt();
                    int successCnt = testResult.getSuccessCnt();
                    long qps = testResult.getServerQPS();
                    boolean pass  = skip ? true : successCnt>0;
                    if (skip) {
                        skipCase++;
                        //场景
                        tdList.add(new TableTD(String.format("%s[skip]",sceneKey), "warning"));
                        //是否通过
                        tdList.add(new TableTD(StringUtils.EMPTY, "warning"));
                        //性能测试
                        tdList.add(new TableTD(StringUtils.EMPTY, "warning"));
                        //错误信息
                        tdList.add(new TableTD(StringUtils.EMPTY, "warning"));
                    }else{
                        //场景
                        tdList.add(new TableTD(String.format("%s",sceneKey)));
                        //是否通过
                        tdList.add(new TableTD(pass ? "pass" : "failed", pass ? "success" : "danger"));
                        //性能测试
                        tdList.add(new TableTD(String.format("loop count:[%s/%s], QPS: %s", successCnt, successCnt+failCnt, qps)));
                        //错误信息
                        if (!pass) {
                            String url = String.format("/errormsg?taskKey=%s&module=%s&task=%s",taskKey, testResult.getModule(), testResult.getTask());
                            String link = String.format("<a href=\"%s\">错误日志</a>", url);
                            tdList.add(new TableTD(link, "danger"));
                        }else{
                            tdList.add(new TableTD(StringUtils.EMPTY));
                            passedCase++;
                        }
                    }
                    tr.setTdList(tdList);
                    tmpList.add(tr);
                    rowSpan1++;
                    rowSpan2++;
                }
                if (moduleTDList == null) {
                    moduleTDList = tmpList.get(0).getTdList();
                }
                //接口
                tmpList.get(0).getTdList().add(0,new TableTD(rowSpan2, sheetKey));
                //接口通过率
                String passRate = String.format("%s%%", rowSpan2 == skipCase ? 0 : passedCase*100/(rowSpan2-skipCase));
                if (skipCase > 0){
                    passRate = String.format("%s [ skip = %s ]", passRate, skipCase);
                }
                tmpList.get(0).getTdList().add(1,new TableTD(rowSpan2, passRate, passedCase+skipCase==rowSpan2 ? "success":"danger"));
                tableList.addAll(tmpList);
            }
            moduleTDList.add(0,new TableTD(rowSpan1, moduleKey));
        }
        return tableList;
    }


    private static Map<String, Map<String, Map<String, TestResult>>> genTable(TaskInfo taskInfo) {
        if (taskInfo == null){
            return MapUtils.EMPTY_MAP;
        }
        Map<String,Map<String,Map<String,TestResult>>> map = Maps.newHashMap();

        List<TestResult> resultList = taskInfo.getResultList();
        if (CollectionUtils.isEmpty(resultList)){
            return map;
        }
        for (TestResult result:resultList){
            String module = result.getModule();
            String sheet = result.getSheet();
            String task = result.getTask();
            Map<String, Map<String, TestResult>> moduleMap = map.get(module);
            if (moduleMap == null){
                moduleMap = Maps.newHashMap();
                map.put(module, moduleMap);
            }

            Map<String, TestResult> sheetMap = moduleMap.get(sheet);
            if (sheetMap == null){
                sheetMap = Maps.newHashMap();
                moduleMap.put(sheet, sheetMap);
            }

            String tsk = String.format("success:[%s]\tfailed:[%s]\tQPS:[%s]", result.getSuccessCnt(),
                    result.getFailedCnt(), result.getServerQPS());
            sheetMap.put(task,result);
        }
        return map;
    }
}
