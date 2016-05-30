/**
 * HomeController.java
 * Created on  20/4/2016 12:17 PM
 * modify on                user            modify content
 * 20/4/2016 12:17 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by micx  on 2016/04/20 12:17 PM.
 */
@Controller
public class HomeController{
//    @Autowired
//    private Audience audience;
    @RequestMapping("/home")
    public String showHomePage(Model model){
//        model.addAttribute("name","spring-mvc");
//        System.out.println(audience);
//        audience.show(Arrays.asList(1,2,34,4,5,4).toArray());
        return "home";
    }

}
