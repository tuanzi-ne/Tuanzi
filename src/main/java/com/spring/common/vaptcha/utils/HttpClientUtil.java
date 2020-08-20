package com.spring.common.vaptcha.utils;

import com.spring.common.vaptcha.domain.HttpResp;
import org.apache.commons.codec.Charsets;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class HttpClientUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    // 发送GET请求
    public static HttpResp getRequest(String path, List<NameValuePair> parametersBody) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(path);
        uriBuilder.setParameters(parametersBody);
        HttpGet get = new HttpGet(uriBuilder.build());
        CloseableHttpClient client = HttpClientBuilder.create().build();
        try {
            HttpResponse response = client.execute(get);
            int code = response.getStatusLine().getStatusCode();
            if (code >= 400) {
                logger.warn("Could not access  resource path: " + path + " Server returned http code: " + code);
            }
            return new HttpResp(EntityUtils.toString(response.getEntity()), code);
        } catch (ClientProtocolException e) {
            throw new Exception("postRequest -- Client protocol exception!", e);
        } catch (IOException e) {
            throw new Exception("postRequest -- IO error!", e);
        } finally {
            get.releaseConnection();
        }
    }

    // 发送POST请求（普通表单形式）
    public static String postForm(String path, List<NameValuePair> parametersBody) throws Exception {
        HttpEntity entity = new UrlEncodedFormEntity(parametersBody, Charsets.UTF_8);
        return postRequest(path, "application/x-www-form-urlencoded", entity);
    }

    // 发送POST请求（JSON形式）
    public static String postJSON(String path, String json) throws Exception {
        StringEntity entity = new StringEntity(json, Charsets.UTF_8);
        return postRequest(path, "application/json", entity);
    }

    // 发送POST请求
    private static String postRequest(String path, String mediaType, HttpEntity entity) throws Exception {
        HttpPost post = new HttpPost(path);
        post.addHeader("Content-Type", mediaType);
        post.addHeader("Accept", "application/json");
        post.setEntity(entity);
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(post);
            int code = response.getStatusLine().getStatusCode();
            if (code >= 400)
                throw new Exception(EntityUtils.toString(response.getEntity()));
            return EntityUtils.toString(response.getEntity());
        } catch (ClientProtocolException e) {
            throw new Exception("postRequest -- Client protocol exception!", e);
        } catch (IOException e) {
            throw new Exception("postRequest -- IO error!", e);
        } finally {
            post.releaseConnection();
        }
    }
}