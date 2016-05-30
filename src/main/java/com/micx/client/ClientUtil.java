/**
 * HttpUtil.java
 * Created on  7/4/2016 7:10 PM
 * modify on                user            modify content
 * 7/4/2016 7:10 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.client;

import com.beust.jcommander.internal.Lists;
import com.micx.client.ast.entity.AssertFailedMsg;
import com.micx.client.ast.enums.AssertType;
import com.micx.client.entity.CaseRequest;
import com.micx.client.entity.TestResult;
import com.micx.client.iface.ReportCallBack;
import com.micx.mvc.service.iface.RunTimeDataService;
import com.micx.threadpool.DefaultThreadPoolImpl;
import com.micx.threadpool.HttpClientThreadPoolImpl;
import com.micx.utils.JsonUtil;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by micx  on 2016/04/07 7:10 PM.
 */
public class ClientUtil {

    public static Future<TestResult> callHttpRequest(final String taskKey, final CaseRequest request,
                                                     final RunTimeDataService runTimeDataService) {

        // 创建HttpClient实例
        HttpDoXClient xClient = new HttpDoXClient();
        xClient.setParamList(request.getParams());
        xClient.setHttpMethod(request.getHttpMethod());
        xClient.setCookieList(request.getCookieList());
        xClient.setRepeatTimes(request.getRepeat());
        xClient.setDomain(request.getDomain());
        xClient.setPath(request.getPath());
        xClient.setTimeOut(request.getTimeOut());
        xClient.setCallBack(new ReportCallBack() {
            public TestResult generateReport(int sucCnt, int failedCnt, long totalCost, long avg) {
                TestResult result = new TestResult(request.getTask(), sucCnt, failedCnt, totalCost, avg);
                result.setModule(request.getModule());
                result.setSheet(request.getSheet());
                return result;
            }
            public void handleResponse(int statusCode,String response) {
                if (runTimeDataService != null) {
                    runTimeDataService.increTaskCnt(taskKey, 1);
                }
            }
            public boolean assertResponse(int status, String response, List<AssertFailedMsg> errMsgList) {
                if (request.getAssertType() == null){
                    AssertFailedMsg assertFailedMsg = new AssertFailedMsg(AssertType.EQUALS, StringUtils.EMPTY, "can not found AssertType", false);
                    errMsgList.add(assertFailedMsg);
                    return false;
                }
                return request.getAssertType().assertResp.assertResponse(request.getExpectResult(), response, errMsgList);
            }
        });
        Future future = DefaultThreadPoolImpl.submit(new InteralTask(taskKey, request.getRepeat(), request.getThreadNum(), request.getTimeOut(), xClient));
        return future;
    }
    static class InteralTask implements Callable<TestResult>{

        private String taskKey;
        private int repeat;
        private int threadCnt;
        private int timeOut = 5000;
        private HttpDoXClient client;

        public InteralTask(int threadCnt, HttpDoXClient client) {
            this.threadCnt = threadCnt;
            this.client = client;
        }

        public InteralTask(String taskKey,int repeat, int threadCnt, int timeOut, HttpDoXClient client) {
            this.repeat = repeat;
            this.taskKey = taskKey;
            this.threadCnt = threadCnt;
            this.timeOut = timeOut;
            this.client = client;
        }

        public TestResult call() throws Exception {
            List<Future<TestResult>> futureList = Lists.newArrayList();
            for (int i = 0; i < threadCnt; i++) {
                Future future = HttpClientThreadPoolImpl.submit(client);
                futureList.add(future);
            }
            TestResult result = null;

            long t = System.currentTimeMillis();
            for (Future<TestResult> future: futureList) {
                TestResult testResult = null;
                try {
                    testResult = future.get();
                } catch (Exception e) {
                    testResult = new TestResult(null, 0, client.getRepeatTimes(), timeOut, 0);
                    AssertFailedMsg assertFailedMsg = new AssertFailedMsg(AssertType.EQUALS,new String(), e.getMessage(), false);
                    testResult.addErrMsg(JsonUtil.parseJson(assertFailedMsg), new AtomicInteger(repeat));
                    System.out.println(e);
                }
                result = mergeResult(result, testResult);
            }
            long totalCost = System.currentTimeMillis() - t;
            result.setCost(totalCost);
            result.setServerAvgCost(totalCost/(result.getFailedCnt()+result.getSuccessCnt()));
            result.setClientAvgCost(result.getTotalCost()/(result.getFailedCnt()+result.getSuccessCnt()));
            result.setServerQPS(1000/result.getServerAvgCost());
            return result;
        }

        private TestResult mergeResult(TestResult result, TestResult testResult) {
            if (result == null){
                return testResult;
            }else if (testResult == null) {
                return result;
            }else{
                int failedCnt = result.getFailedCnt() + testResult.getFailedCnt();
                int successCnt = result.getSuccessCnt() + testResult.getSuccessCnt();
                long totalCost = result.getTotalCost() + testResult.getTotalCost();
                result.setFailedCnt(failedCnt);
                result.setSuccessCnt(successCnt);
                result.setTotalCost(totalCost);
                result.addErrMsg(testResult.getErrMsg());
                return result;
            }
        }
    }
}
