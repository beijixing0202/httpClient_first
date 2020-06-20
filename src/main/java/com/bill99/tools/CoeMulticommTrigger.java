package com.bill99.tools;


import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class CoeMulticommTrigger {

    public void executeTrigger(String triggerGroup,String triggerName){
        //1.创建客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //2.创建请求参数
        HttpHost proxy = new HttpHost("127.0.0.1",8888,"http");
        RequestConfig config = RequestConfig.custom().setConnectTimeout(1000)
                .setConnectionRequestTimeout(2000)
                //.setProxy(proxy)
                .build();
        //3.创建请求
        String login_url = "http://192.168.68.43:8001/inf-ops/login.do";
        HttpPost post = new HttpPost(login_url);
        post.setConfig(config);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username","shengyuan.wang"));
        params.add(new BasicNameValuePair("password","2wsx@WSX"));
        HttpEntity requestEntity  = null;
        try {
            requestEntity  = new UrlEncodedFormEntity(params);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        post.setEntity(requestEntity );

        //4.发送请求，获取响应
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(post);
            if (httpResponse.getStatusLine().getStatusCode() == 200 || httpResponse.getStatusLine().getStatusCode() == 302){
                    String url_trigger = "http://192.168.68.43:8001/inf-ops/schedule/quartz/triggers/op.do";
                    HttpPost post_trigger = new HttpPost(url_trigger);
                    post_trigger.setConfig(config);
                    List<NameValuePair> params_trigger = new ArrayList<NameValuePair>();
                    params_trigger.add(new BasicNameValuePair("triggerGroup",triggerGroup));
                    params_trigger.add(new BasicNameValuePair("opType","fire"));
                    params_trigger.add(new BasicNameValuePair("triggerName",triggerName));
                    params_trigger.add(new BasicNameValuePair("opReason","测试"));
                    params_trigger.add(new BasicNameValuePair("idx","0"));
                    HttpEntity httpEntity_trigger = new UrlEncodedFormEntity(params_trigger);
                    post_trigger.setEntity(httpEntity_trigger);
                    httpResponse = httpClient.execute(post_trigger);
                    if (httpResponse.getStatusLine().getStatusCode() == 200){
                        System.out.println(EntityUtils.toString(httpResponse.getEntity(),"utf8"));
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (httpResponse != null){
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpClient != null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
