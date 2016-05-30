/**
 * HelloWorldService.java
 * Created on  10/4/2016 9:19 PM
 * modify on                user            modify content
 * 10/4/2016 9:19 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.mvc.service.iface;

import com.micx.mvc.service.entity.Nav;

import java.util.List;

/**
 * Created by micx  on 2016/04/10 9:19 PM.
 */
public interface NavigationService {
    List<Nav> getNavigations(String filePath);
    List<String> getFileList(String filePath);
}
