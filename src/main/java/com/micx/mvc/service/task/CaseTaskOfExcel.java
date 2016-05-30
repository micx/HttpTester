/**
 * TestExcelJson.java
 * Created on  10/4/2016 10:39 PM
 * modify on                user            modify content
 * 10/4/2016 10:39 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.mvc.service.task;

import com.beust.jcommander.internal.Lists;
import com.micx.client.ClientUtil;
import com.micx.client.entity.CaseRequest;
import com.micx.client.entity.TestResult;
import com.micx.excel.ExcelUtil;
import com.micx.mvc.service.entity.TaskInfo;
import com.micx.mvc.service.enums.TaskStatus;
import com.micx.mvc.service.iface.ReportService;
import com.micx.mvc.service.iface.RunTimeDataService;
import org.apache.commons.collections.CollectionUtils;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by micx  on 2016/04/10 10:39 PM.
 */
public class CaseTaskOfExcel implements Runnable{
    private String taskKey;
    private List<String> fileNameList;
    private String taskFilePath;
    private String taskFileName;
    private ReportService reportService;
    private RunTimeDataService runTimeDataService;

    public CaseTaskOfExcel(String taskKey, List<String> fileNameList, String taskFilePath, String taskFileName,
                           ReportService reportService, RunTimeDataService runTimeDataService) {
        this.taskKey = taskKey;
        this.fileNameList = fileNameList;
        this.taskFileName = taskFileName;
        this.taskFilePath = taskFilePath;
        this.reportService = reportService;
        this.runTimeDataService = runTimeDataService;
    }

    public void run() {
        List<TestResult> list = testCase(taskKey, fileNameList, runTimeDataService);
        reportService.addTaskInfo(taskFilePath, taskFileName, buildTask(list));
    }

    public List<TestResult> testCase(String taskKey, List<String> fileNameList, RunTimeDataService runTimeDataService){
        List<CaseRequest> requestList = Lists.newArrayList();
        List<Future<TestResult>> futureList = Lists.newArrayList();
        List<TestResult> resultList = Lists.newArrayList();
        try {
            for (String fileName: fileNameList) {
                List<CaseRequest> caseRequests = ExcelUtil.parseRequest(fileName);
                if (CollectionUtils.isNotEmpty(caseRequests)) {
                    requestList.addAll(caseRequests);
                }
            }
            runTimeDataService.initTaskInfoData(taskKey, calcTotalCnt(requestList));
            for (CaseRequest request: requestList){
                if (request.isSkip()) {
                    resultList.add(generateSkipResult(request));
                }else{
                    Future<TestResult> future = ClientUtil.callHttpRequest(taskKey, request, runTimeDataService);
                    futureList.add(future);
                }
            }
            while (futureList.size() > 0) {
                Iterator<Future<TestResult>> iterator = futureList.iterator();
                while (iterator.hasNext()){
                    Future<TestResult> future = iterator.next();
                    if (future.isDone()){
                        TestResult testResult = future.get();
                        resultList.add(testResult);
                        iterator.remove();
                    }
                }
            }
            return  resultList;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private TestResult generateSkipResult(CaseRequest request) {
        TestResult result = new TestResult();
        result.setModule(request.getModule());
        result.setSheet(request.getSheet());
        result.setTask(request.getTask());
        result.setSkip(true);
        result.setSuccessCnt(request.getRepeat() * request.getThreadNum());
        return result;
    }

    private int calcTotalCnt(List<CaseRequest> requestList) {
        if (CollectionUtils.isEmpty(requestList)) {
            return 0;
        }
        int cnt = 0;
        for (CaseRequest request: requestList){
            if (!request.isSkip()){
                cnt += request.getRepeat() * request.getThreadNum();
            }
        }
        return cnt;
    }


    private TaskInfo buildTask(List<TestResult> list) {
        return new TaskInfo(TaskStatus.FINISHED, list);
    }

}
