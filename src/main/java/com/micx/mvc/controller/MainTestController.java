/**
 * HelloWorldController.java
 * Created on  10/4/2016 9:20 PM
 * modify on                user            modify content
 * 10/4/2016 9:20 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.mvc.controller;

import com.beust.jcommander.internal.Lists;
import com.beust.jcommander.internal.Maps;
import com.micx.client.ast.entity.AssertFailedMsg;
import com.micx.client.entity.TestResult;
import com.micx.data.ProcessInfo;
import com.micx.mvc.controller.entity.PageInfo;
import com.micx.mvc.controller.entity.TableTR;
import com.micx.mvc.controller.util.TableUtil;
import com.micx.mvc.service.entity.Nav;
import com.micx.mvc.service.entity.TaskInfo;
import com.micx.mvc.service.enums.TaskStatus;
import com.micx.mvc.service.iface.NavigationService;
import com.micx.mvc.service.iface.ReportService;
import com.micx.mvc.service.iface.RunCaseService;
import com.micx.mvc.service.iface.RunTimeDataService;
import com.micx.utils.JsonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * http://localhost:8080/index
 * Created by micx  on 2016/04/10 9:20 PM.
 */

@Controller
public class MainTestController {
    private static String FILE_PATH;
    @Autowired
    private NavigationService navigationService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private RunTimeDataService runTimeDataService;
    @Autowired
    private RunCaseService runCaseService;

    @PostConstruct
    private void init(){
        FILE_PATH = "/data/appdatas/testFile/";
    }

    @RequestMapping(value="/errormsg",method={RequestMethod.GET})
    public ModelAndView getErrorMsg(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("report");
        String taskKey = request.getParameter("taskKey");
        String module = request.getParameter("module");
        String task = request.getParameter("task");
        String pg = request.getParameter("pg");
        int pageId = NumberUtils.toInt(pg);
        String taskFilePath = FILE_PATH+"task/";
        String taskFileName = taskKey;
        List<Nav> navList = navigationService.getNavigations(FILE_PATH);
        TaskInfo taskInfo = reportService.getTaskInfo(taskFilePath, taskFileName);
        TestResult taskErrorMsg = getTaskErrorMsg(taskInfo, module, task);
        Map<String, AtomicInteger> errMsg = taskErrorMsg.getErrMsg();
        Set<Map.Entry<String, AtomicInteger>> entries = errMsg.entrySet();
        List<AssertFailedMsg> msgList = Lists.newArrayList();
        List<String> actualList = Lists.newArrayList();
        for (Map.Entry<String, AtomicInteger> entry: entries){
            String key = entry.getKey();
            if (key.contains("assertType")){
                try {
                    AssertFailedMsg msg = JsonUtil.parseObject(key, AssertFailedMsg.class);
                    String actual = (String) msg.getActual();
                    actualList.add(actual);
                    msg.setActual("见右边→_→");
                    msgList.add(msg);
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
        String actualJson = actualList.get(pageId);
        if(!JsonUtil.validJson(actualJson)){
            Map<String, String> map = Maps.newHashMap();
            map.put("Actual Response", actualJson);
            actualJson = JsonUtil.parseJson(map);
        }

        List<PageInfo> pageList = genPageList(taskKey, module, task, msgList.size(), pageId);

        mv.addObject("exceptJson", JsonUtil.parseJson(msgList.get(pageId)));
        mv.addObject("actualJson",actualJson);
        mv.addObject("navList", navList);
        mv.addObject("selectedAll", 1);
        mv.addObject("pageList", pageList);
        return mv;
    }

    private List<PageInfo> genPageList(String taskKey, String module, String task, int size, int pageId) {
        List<PageInfo> list = Lists.newArrayList();
        for (int i = 0; i < size; i++) {
            String url = String.format("/errormsg?taskKey=%s&module=%s&task=%s&pg=%s", taskKey, module, task, i);
            PageInfo info = new PageInfo(url, String.valueOf(i+1) , pageId == i ? 1 : 0);
            list.add(info);
        }
        return list;
    }

    private TestResult getTaskErrorMsg(TaskInfo taskInfo, String module, String task) {
        if (taskInfo == null){
            return null;
        }
        List<TestResult> resultList = taskInfo.getResultList();
        if (CollectionUtils.isEmpty(resultList)){
            return null;
        }
        for (TestResult result: resultList){
            if (result.getModule().equals(module) && result.getTask().equals(task)){
                return result;
            }
        }
        return null;
    }

    @RequestMapping(value="/all")
    public ModelAndView getAllExcel(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("report");
        String runTaskStr = request.getParameter("runTask");
        List<Nav> navList = navigationService.getNavigations(FILE_PATH);
        List<String> fileList = navigationService.getFileList(FILE_PATH);

        boolean runTask = StringUtils.isBlank(runTaskStr)? false: BooleanUtils.toBoolean(runTaskStr);

        String taskKey = "runAll";
        String taskFilePath = FILE_PATH+"task/";
        String taskFileName = taskKey;
        if (runTask) {
            runCaseService.runCase(taskKey, fileList,taskFilePath, taskFileName,
                    reportService, runTimeDataService);
            reportService.addTaskInfo(taskFilePath, taskFileName, new TaskInfo(TaskStatus.RUNNING, null));
        }
        int percent = 100;
        String process = StringUtils.EMPTY;
        TaskInfo taskInfo = reportService.getTaskInfo(taskFilePath, taskFileName);
        ProcessInfo processInfo = runTimeDataService.getTaskRunTimeData(taskKey);
        if (processInfo == null){
            percent = 100;
        }else {
            if (processInfo.getTotalCnt().intValue() != 0){
                percent = processInfo.getCurCnt().intValue() * 100 / processInfo.getTotalCnt().intValue();
            }
            process = String.format("%s/%s",processInfo.getCurCnt().intValue(), processInfo.getTotalCnt().intValue());
        }

        List<TableTR> trList = TableUtil.parseTable(taskKey, taskInfo);

        mv.addObject("navList", navList);
        mv.addObject("selectedId", -1);
        mv.addObject("percent", percent);
        mv.addObject("process", process);
        mv.addObject("trList", trList);
        mv.addObject("key", taskKey);
        return mv;
    }

    @RequestMapping(value="/")
    public ModelAndView indexPage(HttpServletRequest request) {
        return new ModelAndView("redirect:/index");
    }

    @RequestMapping(value="/index")
    public ModelAndView getPerExcel(HttpServletRequest request) {

        List<Nav> navList = navigationService.getNavigations(FILE_PATH);
        //welcom就是视图的名称（report.ftl）
        String idStr = request.getParameter("id");
        String runTaskStr = request.getParameter("runTask");
        int id = StringUtils.isBlank(idStr)? -1:NumberUtils.toInt(idStr);
        boolean runTask = StringUtils.isBlank(runTaskStr)? false: BooleanUtils.toBoolean(runTaskStr);
        Nav nav = getSelectedNav(id, navList);
        ModelAndView mv = new ModelAndView("report");
        int percent = 100;
        String process = StringUtils.EMPTY;
        List<TableTR> trList = Lists.newArrayList();
        String taskKey = null;
        if (nav != null){
            taskKey = nav.getName().split("\\.")[0];
            String fileName = FILE_PATH + nav.getName();
            String taskFilePath = FILE_PATH+"task/";
            String taskFileName = taskKey;
            if (runTask) {
                runCaseService.runCase(taskKey, Arrays.asList(fileName), taskFilePath, taskFileName,
                        reportService, runTimeDataService);
                reportService.addTaskInfo(taskFilePath, taskFileName, new TaskInfo(TaskStatus.RUNNING, null));
            }
            TaskInfo taskInfo = reportService.getTaskInfo(taskFilePath,taskFileName);
            ProcessInfo processInfo = runTimeDataService.getTaskRunTimeData(taskKey);
            if (processInfo == null){
                percent = 100;
            }else {
                percent = processInfo.getCurCnt().intValue() * 100 / processInfo.getTotalCnt().intValue();
                process = String.format("%s/%s",processInfo.getCurCnt().intValue(), processInfo.getTotalCnt().intValue());
            }
            trList = TableUtil.parseTable(taskKey, taskInfo);

        }
        mv.addObject("navList", navList);
        mv.addObject("selectedId", id);
        mv.addObject("percent", percent);
        mv.addObject("process", process);
        mv.addObject("trList", trList);
        mv.addObject("key", taskKey);
        return mv;
    }
    @RequestMapping(value="/upload")
    public ModelAndView uploadFile(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("upload");

        return mv;
    }

    private Nav getSelectedNav(int id, List<Nav> navList) {
        for (Nav nav: navList){
            if (nav.getId() == id){
                nav.setSelected(1);
                return nav;
            }
        }
        return null;
    }


}