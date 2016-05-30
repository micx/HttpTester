package com.micx.client; /**
 * HttpClientTest.java
 * Created on  31/3/2016 9:24 PM
 * modify on                user            modify content
 * 31/3/2016 9:24 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

import com.beust.jcommander.internal.Lists;
import com.micx.client.ast.entity.AssertFailedMsg;
import com.micx.client.entity.CookieBO;
import com.micx.client.entity.ParamPair;
import com.micx.client.entity.TestResult;
import com.micx.client.iface.ReportCallBack;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by micx  on 2016/03/31 9:24 PM.
 */
public class HttpDoXClient implements Callable<TestResult> {
    private CloseableHttpClient httpClient;
    private List<ParamPair> paramList;
    private String domain;
    private String path;
    private List<CookieBO> cookieList;
    private int repeatTimes = 1;
    private int timeOut = 5000;
    private ReportCallBack callBack;
    private HttpMethod httpMethod;

    private List<NameValuePair> parseParams() {
        List<NameValuePair> formparams = Lists.newArrayList();
        if (paramList == null) {
            return formparams;
        }
        for (ParamPair paramPair : paramList) {
            formparams.add(new BasicNameValuePair(paramPair.getKey(), paramPair.getValue()));
        }
        return formparams;
    }

    private CookieStore parseCookieStore() {
        CookieStore cookieStore = new BasicCookieStore();
        if (CollectionUtils.isEmpty(cookieList)){
            return cookieStore;
        }
        for (CookieBO cookie : cookieList) {
            BasicClientCookie basicClientCookie = new BasicClientCookie(cookie.getName(), cookie.getValue());
            basicClientCookie.setDomain(cookie.getDomain());
            basicClientCookie.setPath(cookie.getPath());
            cookieStore.addCookie(basicClientCookie);
        }
        return cookieStore;
    }

    public TestResult call() throws Exception {
        httpClient = HttpClients.custom()
                .setDefaultCookieStore(parseCookieStore())
                .build();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();//设置请求和传输超时时间
        HttpGet httpGet = null;
        HttpPost httpPost = null;
        if (httpMethod == HttpMethod.GET){
            String url = generateUrl();

            httpGet = new HttpGet(url);
            httpGet.setConfig(requestConfig);
            // 创建参数队列
            httpGet.setHeader("Accept", "image/gif, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-com.micx.excel, application/vnd.ms-powerpoint, application/msword, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, */*");
            httpGet.setHeader("Accept-Language", "zh-cn");
            httpGet.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; InfoPath.2; .NET4.0C; .NET4.0E)");
        }else if(httpMethod == HttpMethod.POST){
            httpPost = new HttpPost(String.format("http://%s%s",domain,path));

            httpPost.setConfig(requestConfig);
            // 创建参数队列
            List<NameValuePair> formparams = parseParams();

            try {
                UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
                httpPost.setEntity(uefEntity);
            } catch (Exception e) {
                System.out.println(e);
            }
            httpPost.setHeader("Accept", "image/gif, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-com.micx.excel, application/vnd.ms-powerpoint, application/msword, application/xaml+xml, application/x-ms-xbap, application/x-ms-application, */*");
            httpPost.setHeader("Accept-Language", "zh-cn");
            httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; InfoPath.2; .NET4.0C; .NET4.0E)");
        }


        List<AssertFailedMsg> astResults = Lists.newArrayList();
        long s = System.currentTimeMillis();
        int sucCnt = 0;
        int failedCnt = 0;
        for (int i = 0; i < repeatTimes; i++) {
            String respStr = null;
            try {
                HttpResponse response = null;
                try {
                    if (httpMethod == HttpMethod.GET){
                        response = httpClient.execute(httpGet);
                    }else if (httpMethod == HttpMethod.POST) {
                        response = httpClient.execute(httpPost);
                    }
                } catch (Exception e) {
                    failedCnt++;
                    callBack.assertResponse(0, String.format("%s [timeout = %s ms]", e.getMessage(), timeOut),astResults);
                    System.out.println(e);
                    continue;
                }

                HttpEntity entity = response.getEntity();
                respStr = null;
                if (entity != null) {
                    respStr = EntityUtils.toString(entity, "UTF-8");
                    EntityUtils.consume(entity);
                }
                if (response.getStatusLine().getStatusCode() == 200){
                    boolean assertResult = callBack.assertResponse(response.getStatusLine().getStatusCode(), respStr,astResults);
                    if (assertResult) {
                        sucCnt++;
                    }else{
                        failedCnt++;
                    }
                }else{
                    callBack.assertResponse(response.getStatusLine().getStatusCode(),
                            String.format("code:%s, content:%s ", response.getStatusLine().getStatusCode(),
                                    respStr = EntityUtils.toString(entity)),astResults);
                    failedCnt++;
                }
            } catch (Exception e) {
                System.out.println(e);
            }finally {
                callBack.handleResponse(200, respStr);
            }
        }
        long total = System.currentTimeMillis() - s;
        TestResult report = callBack.generateReport(sucCnt, failedCnt, total, total / repeatTimes);
        report.addErrMsg(astResults);
        return report;
    }

    private String generateUrl() {

        return String.format("http://%s%s?%s", domain, path, buildParams());
    }

    private String buildParams() {
        if (CollectionUtils.isEmpty(paramList)){
            return StringUtils.EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        for (ParamPair paramPair: paramList){
            sb.append(String.format("%s=%s&",paramPair.getKey(), paramPair.getValue()));
        }
        return sb.toString();
    }

    public List<ParamPair> getParamList() {
        return paramList;
    }

    public void setParamList(List<ParamPair> paramList) {
        this.paramList = paramList;
    }

    public List<CookieBO> getCookieList() {
        return cookieList;
    }

    public void setCookieList(List<CookieBO> cookieList) {
        this.cookieList = cookieList;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getRepeatTimes() {
        return repeatTimes;
    }

    public void setRepeatTimes(int repeatTimes) {
        this.repeatTimes = repeatTimes;
    }

    public ReportCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(ReportCallBack callBack) {
        this.callBack = callBack;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);

    }
}

