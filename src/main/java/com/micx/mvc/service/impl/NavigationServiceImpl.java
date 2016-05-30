/**
 * HelloWorldServiceImpl.java
 * Created on  10/4/2016 9:19 PM
 * modify on                user            modify content
 * 10/4/2016 9:19 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.mvc.service.impl;

import com.beust.jcommander.internal.Lists;
import com.micx.excel.file.FileUtil;
import com.micx.mvc.service.entity.Nav;
import com.micx.mvc.service.iface.NavigationService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by micx  on 2016/04/10 9:19 PM.
 */
@Service
public class NavigationServiceImpl implements NavigationService {

    public List<Nav> getNavigations(String filePath) {
        return convert2Navs(FileUtil.getFiles(filePath, FileUtil.WITHOUT_PATH));

    }

    public List<String> getFileList(String filePath) {
        return FileUtil.getFiles(filePath, FileUtil.WITH_PATH);
    }

    private List<Nav> convert2Navs(List<String> files) {
        List<Nav> list = Lists.newArrayList();
        if (CollectionUtils.isEmpty(files)){
            return list;
        }
        Collections.sort(files);
        for (int i = 0; i < files.size(); i++) {
            list.add(new Nav(i, files.get(i)));
        }
        return list;
    }
}
