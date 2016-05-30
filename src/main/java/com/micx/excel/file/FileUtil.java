/**
 * FileTest.java
 * Created on  12/4/2016 10:36 PM
 * modify on                user            modify content
 * 12/4/2016 10:36 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.excel.file;

import com.beust.jcommander.internal.Lists;

import java.io.*;
import java.util.List;

/**
 * Created by micx  on 2016/04/12 10:36 PM.
 */
public class FileUtil {

    /*
     * 通过递归得到某一路径下所有的目录及其文件
     */
    public static final int WITH_PATH = 1;
    public static final int WITHOUT_PATH = 2;

    public static List<String> getFiles(String filePath, int type) {
        List<String> filePathList = Lists.newArrayList();
        File root = new File(filePath);
        if (root == null) {
            return filePathList;
        }
        File[] files = root.listFiles();
        if (files == null || files.length == 0) {
            return filePathList;
        }
        for (File file : files) {
            if (file.getName().startsWith("~")) {
                continue;
            }
            if (file.isDirectory()) {
//                getFiles(file.getAbsolutePath());
//                filelist.add(file.getAbsolutePath());
            } else if (!file.isHidden()) {
                switch (type) {
                    case WITH_PATH:
                        filePathList.add(filePath + file.getName());
                        break;
                    case WITHOUT_PATH:
                        filePathList.add(file.getName());
                        break;
                }
            }
        }
        return filePathList;
    }

    public static void writeFile(String path, String fileName, String content) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        file = new File(path, fileName);
        if (!file.exists())
            file.createNewFile();
        FileOutputStream out = new FileOutputStream(file, false);
        StringBuffer sb = new StringBuffer();
        sb.append(content);
        out.write(sb.toString().getBytes("utf-8"));
        out.close();
    }

    public static String readFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists() || file.isDirectory())
            throw new FileNotFoundException();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String temp = null;
        StringBuffer sb = new StringBuffer();
        temp = br.readLine();
        while (temp != null) {
            sb.append(temp + "\n");
            temp = br.readLine();
        }
        return sb.toString();
    }


}
