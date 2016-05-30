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

import com.micx.excel.file.FileUtil;
import com.micx.mvc.service.entity.Nav;
import com.micx.mvc.service.iface.NavigationService;
import com.micx.mvc.service.iface.ReportService;
import com.micx.mvc.service.iface.RunTimeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * http://localhost:8080/index
 * Created by micx  on 2016/04/10 9:20 PM.
 */

@Controller
public class WikiController {
    private static String FILE_PATH;
    @Autowired
    private NavigationService navigationService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private RunTimeDataService runTimeDataService;

    @PostConstruct
    private void init(){
        FILE_PATH = "/data/testFile/";
    }

    @RequestMapping(value="/wiki",method={RequestMethod.GET})
    public ModelAndView getErrorMsg(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("wiki");
        String mdText = "";
        try {
            String filePath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
            filePath = filePath+"wiki/wiki.md";
            mdText = FileUtil.readFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Nav> navList = navigationService.getNavigations(FILE_PATH);
        mv.addObject("navList", navList);
        mv.addObject("selectedId", -2);
        mv.addObject("mdText", mdText);
        return mv;
    }




}