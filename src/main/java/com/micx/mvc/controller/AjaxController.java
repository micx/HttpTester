/**
 * LoginController.java
 * Created on  22/4/2016 7:01 PM
 * modify on                user            modify content
 * 22/4/2016 7:01 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.mvc.controller;

import com.micx.data.ProcessInfo;
import com.micx.mvc.service.iface.RunTimeDataService;
import com.micx.threadpool.DefaultThreadPoolImpl;
import com.micx.threadpool.HttpClientThreadPoolImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by micx  on 2016/04/22 7:01 PM.
 */
@Controller
@RequestMapping("/ajax/*")
public class AjaxController {
    @Autowired
    private RunTimeDataService runTimeDataService;
    @RequestMapping(value="process")
    public @ResponseBody Map<String,Object> process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String key = request.getParameter("taskKey");
        ProcessInfo processInfo = null;
        if (key != null) {
            processInfo = runTimeDataService.getTaskRunTimeData(key);
        }
        Map<String,Object> map = new HashMap<String,Object>();
        int percent;
        String percentMsg = StringUtils.EMPTY;
        if (processInfo == null){
            percent = 105;
        }else {
            percent = processInfo.getCurCnt().intValue() * 100 / processInfo.getTotalCnt().intValue();
            if (percent >= 100){
                processInfo.getCurCnt().addAndGet((int) Math.ceil(processInfo.getTotalCnt().intValue()*1.0/100));
            }
            percentMsg = String.format("%s/%s",processInfo.getCurCnt().intValue(),processInfo.getTotalCnt().intValue());
        }
        map.put("percent", percent);
        map.put("percentMsg", percentMsg);
        return map;
    }

    @RequestMapping(value="thread")
    public @ResponseBody Map<String,Object> threadStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String,Object> map = new HashMap<String,Object>();

        String fixedStatus = DefaultThreadPoolImpl.getThreadPoolStatus();
        String cachedStatus = HttpClientThreadPoolImpl.getThreadPoolStatus();
        String threadStatus = String.format(
                "---------------ThreadStatus----------\n" +
                "fixed:\t[%s]\ncached:\t[%s]\n" +
                "-------------------------------------\n",fixedStatus, cachedStatus);

        DecimalFormat df = new DecimalFormat("0.00") ;
        long totalMem = Runtime.getRuntime().totalMemory();
        String totalM = df.format(totalMem / 1000000F) + " MB";

        long freeMem = Runtime.getRuntime().freeMemory();
        String usedMem = df.format((totalMem - freeMem) / 1000000F) + " MB";
        String freeM = df.format(freeMem / 1000000F) + " MB";

        String memStatus = String.format(
                "---------------MemStatus-------------\n" +
                "totalMem:\t%s/%s\nfreeMem:\t%s\n"+
                "-------------------------------------\n",usedMem, totalM, freeM);
        map.put("threadStatus",threadStatus);
        map.put("memStatus",memStatus);
        return map;
    }

}
