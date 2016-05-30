/**
 * UploadAction.java
 * Created on  10/5/2016 10:05 PM
 * modify on                user            modify content
 * 10/5/2016 10:05 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * Created by micx  on 2016/05/10 10:05 PM.
 */
@Controller
public class UploadAction {

    @RequestMapping(value = "/upload.do")
    public ModelAndView upload(@RequestParam(value = "file", required = false) MultipartFile file,
                              HttpServletRequest request, ModelMap model) {

        String path = "/data/appdatas/testFile/";
        String fileName = file.getOriginalFilename();
        File targetFile = new File(path, fileName);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }else {
            targetFile.delete();
        }
        //保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/index");
    }

}
