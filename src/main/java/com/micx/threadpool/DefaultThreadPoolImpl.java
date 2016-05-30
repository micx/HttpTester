/**
 * ThreadPool.java
 * Created on  13/3/15 下午1:41
 * modify on                user            modify content
 * 13/3/15 下午1:41        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.threadpool;

import java.util.concurrent.*;

/**
 * Created by micx  on 2015/03/13 下午1:41.
 */
public class DefaultThreadPoolImpl {
    private static final int POOL_SIZE = 50;
    private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(POOL_SIZE);

    public static void submit(Runnable runnable) {
        Future<?> future = fixedThreadPool.submit(runnable);
    }

    public static Future submit(Callable callable) {
        Future<?> future = fixedThreadPool.submit(callable);
        return future;
    }

    public static void shutDown() {
        fixedThreadPool.shutdown();
    }

    public static String getThreadPoolStatus() {
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) fixedThreadPool;
        long taskCount = threadPool.getTaskCount();
        long completedTaskCount = threadPool.getCompletedTaskCount();
        int activeCount = threadPool.getActiveCount();
        long poolSize = threadPool.getPoolSize();
        return String.format("thread:%s/%s - task:%s/%s", activeCount, poolSize, completedTaskCount, taskCount);
    }
}
