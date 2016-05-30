/**
 * TaskInfoData.java
 * Created on  14/4/2016 1:04 PM
 * modify on                user            modify content
 * 14/4/2016 1:04 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.data;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by micx  on 2016/04/14 1:04 PM.
 */
public class TaskInfoData {

    public static volatile ConcurrentHashMap<String, ProcessInfo> map = new ConcurrentHashMap<String, ProcessInfo>();

    public static void increTaskCnt(String taskKey, int cnt){
        map.get(taskKey).getCurCnt().addAndGet(cnt);
    }

    public static void initTask(String taskKey, int totalCnt) {
        if (map.get(taskKey) == null){
            synchronized (TaskInfoData.class){
                if (map.get(taskKey) == null){
                    map.put(taskKey, new ProcessInfo(new AtomicInteger(0), new AtomicInteger(totalCnt)));
                }
            }
        }
        map.get(taskKey).setCurCnt(new AtomicInteger(0));
        map.get(taskKey).setTotalCnt(new AtomicInteger(totalCnt));
    }
}
