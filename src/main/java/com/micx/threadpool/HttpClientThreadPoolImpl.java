/**
 * ThreadPool.java  
 * Created on  13/3/15 下午1:41
 * modify on                user            modify content
 * 13/3/15 下午1:41        micx
 *
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.threadpool;

import java.util.concurrent.*;

/**
 * Created by micx  on 2015/03/13 下午1:41.
 */
public class HttpClientThreadPoolImpl {
    private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    public static void submit(Runnable runnable){
        Future<?> future = cachedThreadPool.submit(runnable);
    }
    public static Future submit(Callable callable){
        Future<?> future = cachedThreadPool.submit(callable);
        return future;
    }

    public static void shutDown() {
        cachedThreadPool.shutdown();
    }

    public static String getThreadPoolStatus(){
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) HttpClientThreadPoolImpl.cachedThreadPool;
        long taskCount = threadPool.getTaskCount();
        long completedTaskCount = threadPool.getCompletedTaskCount();
        int activeCount = threadPool.getActiveCount();
        long poolSize = threadPool.getPoolSize();
        return String.format("thread:%s/%s - task:%s/%s", activeCount, poolSize, completedTaskCount, taskCount);
    }
}
